package com.sngular.wheatherapp.domain.models.current

data class CurrentClimate(
    val city : String,
    val wheather : List<Weather>,
    var temperature : Temperature
)