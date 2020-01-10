package com.sngular.wheatherapp.data.mapper.forecast

import com.sngular.core.arch.transformer.Transformer
import com.sngular.wheatherapp.domain.models.forecast.ForecastClimate
import com.sngular.wheatherapp.data.models.forecast.ForecastResponse

class ForecastResponseToForecastClimate(
    private val forecastClimateToWeatherDate: ForecastClimateToWeatherDate
) : Transformer<ForecastResponse, ForecastClimate>() {

    override fun transform(value: ForecastResponse): ForecastClimate {
        return ForecastClimate(weatherDates = forecastClimateToWeatherDate
            .transformCollection(value.list.orEmpty()))
    }
}