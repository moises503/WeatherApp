package com.sngular.core

import android.app.Application
import com.sngular.core.di.coreModule
import com.sngular.wheatherapp.presentation.di.weatherModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class WeatherApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@WeatherApp)
            modules(arrayListOf(coreModule, weatherModule))
        }
    }
}