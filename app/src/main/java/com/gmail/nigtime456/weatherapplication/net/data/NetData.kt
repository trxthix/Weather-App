/*
 * Сreated by Igor Pokrovsky. 2020/5/5
 */

package com.gmail.nigtime456.weatherapplication.net.data

class NetData<T> constructor(val data: T, val timestamp: Long) {

    override fun toString(): String {
        return "NetData(data=$data, timestamp=$timestamp)"
    }
}