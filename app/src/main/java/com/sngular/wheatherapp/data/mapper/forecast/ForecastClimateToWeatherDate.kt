package com.sngular.wheatherapp.data.mapper.forecast

import com.sngular.core.arch.transformer.Transformer
import com.sngular.wheatherapp.data.mapper.current.MainTemperatureToTemperature
import com.sngular.wheatherapp.data.mapper.current.WeatherListResponseToWeatherList
import com.sngular.wheatherapp.data.models.forecast.ForecastClimate
import com.sngular.wheatherapp.domain.models.forecast.WeatherDate

class ForecastClimateToWeatherDate(
    private val weatherListResponseToWeatherList: WeatherListResponseToWeatherList,
    private val mainTemperatureToTemperature: MainTemperatureToTemperature
) : Transformer<ForecastClimate, WeatherDate>() {

    override fun transform(value: ForecastClimate): WeatherDate {
        return WeatherDate(
            weather = weatherListResponseToWeatherList.transformCollection(value.weather.orEmpty()),
            temperature = mainTemperatureToTemperature.transform(value.main),
            date = value.dtTxt ?: ""
        )
    }
}