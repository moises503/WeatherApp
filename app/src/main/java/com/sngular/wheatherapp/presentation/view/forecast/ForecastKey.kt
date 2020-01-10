package com.sngular.wheatherapp.presentation.view.forecast

import android.annotation.SuppressLint
import android.location.Location
import com.sngular.core.navigation.BaseFragment
import com.sngular.core.navigation.BaseKey
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class ForecastKey(val tag: String, val city: String, val location: Location?) : BaseKey() {
    constructor(city: String, location: Location?) : this("ForecastKey", city, location)
    override fun createFragment(): BaseFragment = ForecastFragment()
}