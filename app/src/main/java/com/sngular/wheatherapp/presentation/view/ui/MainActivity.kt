package com.sngular.wheatherapp.presentation.view.ui

import android.annotation.SuppressLint
import android.content.Context
import com.sngular.core.navigation.BaseKey
import com.sngular.core.navigation.BaseNavigationActivity
import com.sngular.wheatherapp.R
import com.sngular.wheatherapp.presentation.view.ui.weather.CurrentWeatherKey

class MainActivity : BaseNavigationActivity() {

    override fun getLayout(): Int = R.layout.activity_main

    override fun getFragmentContainer(): Int = R.id.fragments_container

    override fun defaultFirstKey(): BaseKey =
        CurrentWeatherKey()

    override fun getSystemService(name: String): Any? = when (name) {
        TAG -> this
        else -> super.getSystemService(name)
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName

        @SuppressLint("WrongConstant")
        operator fun get(context: Context): MainActivity {
            return context.getSystemService(TAG) as MainActivity
        }
    }
}
