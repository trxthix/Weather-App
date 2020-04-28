/*
 * Сreated by Igor Pokrovsky. 2020/4/27
 */

package com.nigtime.weatherapplication.ui.screens.pager

import com.nigtime.weatherapplication.db.repository.ForecastCitiesRepository
import com.nigtime.weatherapplication.ui.screens.common.BasePresenter
import com.nigtime.weatherapplication.utility.rx.SchedulerProvider

class PagerCityPresenter(
    schedulerProvider: SchedulerProvider,
    private val forecastCitiesRepository: ForecastCitiesRepository
) : BasePresenter<PagerCityView>(
    schedulerProvider
) {

    fun provideCities() {
        forecastCitiesRepository.getListCityForForecast()
            .subscribeOn(schedulerProvider.syncDatabase())
            .observeOn(schedulerProvider.ui())
            .subscribeAndHandleError(false) { list ->
                require(list.isNotEmpty()) { "pager screen must not get empty cities list!" }
                getView()?.submitList(list)
            }
    }
}