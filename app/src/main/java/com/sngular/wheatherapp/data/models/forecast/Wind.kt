package com.sngular.wheatherapp.data.models.forecast

import com.google.gson.annotations.SerializedName

data class Wind(

	@field:SerializedName("deg")
	val deg: Int? = null,

	@field:SerializedName("speed")
	val speed: Double? = null
)