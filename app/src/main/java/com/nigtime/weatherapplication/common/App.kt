/*
 * Сreated by Igor Pokrovsky. 2020/4/28
 */

package com.nigtime.weatherapplication.common

import android.app.Application
import androidx.preference.PreferenceManager
import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.common.di.AppContainer
import com.nigtime.weatherapplication.common.log.CustomLogger
import leakcanary.AppWatcher
import leakcanary.LeakCanary

class App : Application() {

    companion object {
        lateinit var INSTANCE: App
    }

    val logger = CustomLogger("deb", true)

    lateinit var appContainer: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        setCanary()
        setupPreferences()
        setContainer()
        setLogger()
    }

    private fun setupPreferences() {
        PreferenceManager.setDefaultValues(this, R.xml.main_preferences, false)
        PreferenceManager.setDefaultValues(this, R.xml.data_preferences, false)
        PreferenceManager.setDefaultValues(this, R.xml.unit_preferences, false)
    }

    /**
     * Leak Canary отслеживает ЖЦ объектов и детектит утечки объектов
     */
    private fun setCanary() {
        AppWatcher.config = AppWatcher.config.copy(
            enabled = true,
            watchActivities = true,
            watchFragments = true,
            watchFragmentViews = true,
            watchViewModels = true,
            watchDurationMillis = 5000
        )
        LeakCanary.config = LeakCanary.config.copy(retainedVisibleThreshold = 1)
    }

    private fun setContainer() {
        appContainer = AppContainer(this)
    }

    /**
     * Включить/отключить логирование
     */
    private fun setLogger() {
        CustomLogger.printLog(true)
    }
}