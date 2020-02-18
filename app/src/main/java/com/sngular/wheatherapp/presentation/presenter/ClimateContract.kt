package com.sngular.wheatherapp.presentation.presenter

import com.sngular.wheatherapp.domain.models.current.CurrentClimate
import com.sngular.wheatherapp.domain.models.forecast.ForecastClimate
import com.sngular.wheatherapp.domain.models.Location

interface ClimateContract {
    interface Presenter {
        fun retrieveCurrentClimate(location : Location)
        fun retrieveForecastClimate(location: Location)
        fun onStop()
    }

    interface BaseView {
        fun showLoader()
        fun hideLoader()
        fun showError(message : String)
        fun displayCurrentWeather(currentClimate: CurrentClimate, animation : String)
        fun displayForecastClimate(forecastClimate: ForecastClimate)
    }

    interface CurrentWeatherView :
        BaseView {
        override fun displayForecastClimate(forecastClimate: ForecastClimate) = Unit
    }

    interface ForecastClimateView :
        BaseView {
        override fun displayCurrentWeather(currentClimate: CurrentClimate, animation: String) =
            Unit
    }

    interface StringResources {
        fun currentClimateErrorMessage() : String
        fun forecastClimateErrorMessage() : String
    }
}