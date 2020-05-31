/*
 * Сreated by Igor Pokrovsky. 2020/5/11
 */

package com.gmail.nigtime456.weatherapplication.net.mappers

import com.gmail.nigtime456.weatherapplication.domain.forecast.SunInfo
import com.gmail.nigtime456.weatherapplication.net.json.JsonCurrentData
import javax.inject.Inject

class SunInfoMapper  @Inject constructor(){

    fun map(jsonCurrentData: JsonCurrentData): SunInfo {
        return SunInfo("T", "T")
    }
}