/*
 * Сreated by Igor Pokrovsky. 2020/5/25
 */

package com.gmail.nigtime456.weatherapplication.net.mappers

import com.gmail.nigtime456.weatherapplication.data.forecast.DailyForecast
import com.gmail.nigtime456.weatherapplication.net.json.JsonDailyData
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class DailyWeatherMapper @Inject constructor() {

    private companion object {
        private const val DATE_PATTERN = "yyyy-MM-dd"
    }

    fun map(index: Int, jsonDailyData: JsonDailyData): DailyForecast.Weather {
        val maxTemp = jsonDailyData.maxTemp
        val minTemp = jsonDailyData.maxTemp
        val ico = WeatherConditionHelper.getIconByCode(jsonDailyData.weather.icon)

        val dateFormatter = SimpleDateFormat(DATE_PATTERN, Locale.ROOT)
        val dateTime = dateFormatter.parse(jsonDailyData.date)?.time ?: 0

        return DailyForecast.Weather(maxTemp, minTemp, ico, index, dateTime)
    }
}