package com.sngular.wheatherapp.presentation.view.ui.forecast

import android.annotation.SuppressLint
import com.sngular.core.navigation.BaseFragment
import com.sngular.core.navigation.BaseKey
import com.sngular.wheatherapp.presentation.common.CurrentLocation
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class ForecastKey(val tag: String, val city: String, val location: CurrentLocation?) : BaseKey() {
    constructor(city: String, location: CurrentLocation?) : this("ForecastKey", city, location)
    override fun createFragment(): BaseFragment =
        ForecastFragment()
}