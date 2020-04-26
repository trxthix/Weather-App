/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.ui.screens.splash

import com.nigtime.weatherapplication.db.service.AppDatabase
import com.nigtime.weatherapplication.trash.CitiesMarshallingHelper
import com.nigtime.weatherapplication.ui.screens.common.App
import com.nigtime.weatherapplication.ui.screens.common.BasePresenter
import com.nigtime.weatherapplication.utility.rx.MainSchedulerProvider
import com.nigtime.weatherbitapp.uselles.storage.room.repository.RoomDictionaryWriter
import com.nigtime.weatherbitapp.uselles.storage.utils.AssetCityProvider
import io.reactivex.Single
import java.util.zip.ZipInputStream

//TODO Должен копировать базу данных
class WrongSplashPresenter : BasePresenter<SplashView>(MainSchedulerProvider.INSTANCE, TAG) {

    companion object {
        private const val TAG = "wrong_splash"
    }

    fun checkAndSetCitiesDictionary() {
        RoomDictionaryWriter(AppDatabase.Instance.get(App.INSTANCE).geoCityDao())
            .isDictionaryWritten()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe(this::onCheckSuccess, this::onStreamError)
            .disposeOnDetach()
    }

    private fun onCheckSuccess(result: Boolean) {
        if (result) {
            onWrithingCompleted()
        } else {
            writeCities()
        }
    }

    private fun writeCities() {
        val citiesDictionaryProvider = AssetCityProvider(App.INSTANCE)
        val marshallingHelper = CitiesMarshallingHelper()
        val dictionaryWriter =
            RoomDictionaryWriter(AppDatabase.Instance.get(App.INSTANCE).geoCityDao())
        Single.just(citiesDictionaryProvider.getZippedCitiesDictionary())
            .map { ZipInputStream(it) }
            .doOnSuccess { it.nextEntry }
            .map { it.bufferedReader() }
            .toFlowable()
            .flatMap { marshallingHelper.readFromReader(it) }
            .doOnNext { dictionaryWriter.writeDictionaryCity(it) }
            .ignoreElements()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe(this::onWrithingCompleted, this::rethrowError)
            .disposeOnDetach()
    }

    private fun onWrithingCompleted() {
        getView()?.playAnimation()
        getView()?.delayedLoadPagerScreen()
    }

}