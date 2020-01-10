package com.sngular.wheatherapp.data.mapper.current

import com.sngular.core.arch.transformer.Transformer
import com.sngular.wheatherapp.data.models.current.CurrentClimateResponse
import com.sngular.wheatherapp.domain.models.current.CurrentClimate

class CurrentWeatherResponseToCurrentWeather(
    private val weatherListResponseToWeatherList: WeatherListResponseToWeatherList,
    private val mainTemperatureToTemperature: MainTemperatureToTemperature
) :
    Transformer<CurrentClimateResponse, CurrentClimate>() {
    override fun transform(value: CurrentClimateResponse): CurrentClimate {
        return CurrentClimate(
            city = value.name ?: "",
            wheather = weatherListResponseToWeatherList
                .transformCollection(values = value.weather.orEmpty()),
            temperature = mainTemperatureToTemperature.transform(value.main)
        )
    }
}