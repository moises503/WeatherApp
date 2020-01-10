package com.sngular.wheatherapp.domain.provider

import com.sngular.wheatherapp.domain.usecase.CurrentClimateUseCase
import com.sngular.wheatherapp.domain.usecase.ForecastClimateUseCase

interface ClimateUseCaseProvider {
    fun providesCurrentWeatherUseCase() : CurrentClimateUseCase
    fun providesForecastClimate() : ForecastClimateUseCase
}