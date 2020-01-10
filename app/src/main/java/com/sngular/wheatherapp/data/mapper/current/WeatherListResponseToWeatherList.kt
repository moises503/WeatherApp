package com.sngular.wheatherapp.data.mapper.current

import com.sngular.core.arch.transformer.Transformer
import com.sngular.wheatherapp.data.models.current.WeatherItem
import com.sngular.wheatherapp.domain.models.current.Weather

class WeatherListResponseToWeatherList : Transformer<WeatherItem, Weather>() {
    override fun transform(value: WeatherItem): Weather {
        return Weather(
            id = value.id ?: 0,
            main = value.main ?: "",
            description = value.description ?: "",
            icon = value.icon ?: ""
        )
    }
}