package com.sngular.wheatherapp.domain.repository

import com.sngular.wheatherapp.domain.models.current.CurrentClimate
import com.sngular.wheatherapp.domain.models.forecast.ForecastClimate
import com.sngular.wheatherapp.domain.models.Location
import io.reactivex.Single

interface ClimateRepository {
    fun retrieveCurrentClimate(location : Location) : Single<CurrentClimate>
    fun retrieveForecastClimate(location: Location) : Single<ForecastClimate>
}