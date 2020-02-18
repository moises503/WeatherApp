package com.sngular.wheatherapp.presentation.viewmodel

import com.sngular.wheatherapp.domain.models.current.CurrentClimate
import com.sngular.wheatherapp.domain.models.forecast.ForecastClimate

sealed class ClimateState {
    class SuccessClimate(val currentClimate: CurrentClimate, val animation : String) : ClimateState()
    class SuccessForeCast(val forecastClimate: ForecastClimate): ClimateState()
    class Error(val error: String) : ClimateState()
}