package com.sngular.wheatherapp.presentation.view.ui.weather

import android.annotation.SuppressLint
import com.sngular.core.navigation.BaseFragment
import com.sngular.core.navigation.BaseKey
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class CurrentWeatherKey(val tag: String) : BaseKey() {
    constructor() : this("CurrentWeatherKey")
    override fun createFragment(): BaseFragment =
        CurrentWeatherFragment()
}