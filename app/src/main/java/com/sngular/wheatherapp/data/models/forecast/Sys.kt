package com.sngular.wheatherapp.data.models.forecast

import com.google.gson.annotations.SerializedName

data class Sys(

	@field:SerializedName("pod")
	val pod: String? = null
)