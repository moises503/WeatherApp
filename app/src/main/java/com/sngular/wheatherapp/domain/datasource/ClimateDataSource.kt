package com.sngular.wheatherapp.domain.datasource

import com.sngular.wheatherapp.domain.models.current.CurrentClimate
import com.sngular.wheatherapp.domain.models.forecast.ForecastClimate
import io.reactivex.Single

interface ClimateDataSource {
    fun retrieveCurrentClimate(latitude : Double, longitude : Double) : Single<CurrentClimate>
    fun retrieveForecastClimate(latitude: Double, longitude: Double) : Single<ForecastClimate>
}