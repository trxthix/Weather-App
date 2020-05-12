/*
 * Сreated by Igor Pokrovsky. 2020/5/9
 */

package com.nigtime.weatherapplication.domain.settings

import androidx.annotation.StringRes
import com.nigtime.weatherapplication.R

sealed class UnitOfLength {
    abstract fun convert(km: Double): Double

    @StringRes
    abstract fun getFormattingPattern(): Int

    object Kilometre : UnitOfLength() {
        override fun convert(km: Double): Double = km

        override fun getFormattingPattern(): Int = R.string.units_km_f
    }

    object Miles : UnitOfLength() {
        override fun convert(km: Double): Double = km / 1.61

        override fun getFormattingPattern(): Int = R.string.units_mi_f

    }
}