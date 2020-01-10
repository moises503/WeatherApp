package com.sngular.wheatherapp.domain.models.forecast

import com.sngular.wheatherapp.domain.models.current.Temperature
import com.sngular.wheatherapp.domain.models.current.Weather

data class WeatherDate(val weather: List<Weather>, val temperature: Temperature, val date: String)