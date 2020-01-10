package com.sngular.wheatherapp.data.models.forecast

import com.google.gson.annotations.SerializedName
import com.sngular.wheatherapp.data.models.current.Main
import com.sngular.wheatherapp.data.models.current.WeatherItem

data class ForecastClimate(

	@field:SerializedName("dt")
	val dt: Int? = null,

	@field:SerializedName("dt_txt")
	val dtTxt: String? = null,

	@field:SerializedName("weather")
	val weather: List<WeatherItem>? = null,

	@field:SerializedName("main")
	val main: Main,

	@field:SerializedName("clouds")
	val clouds: Clouds? = null,

	@field:SerializedName("sys")
	val sys: Sys? = null,

	@field:SerializedName("wind")
	val wind: Wind? = null
)