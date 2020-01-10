package com.sngular.wheatherapp.data.repository

import com.sngular.wheatherapp.domain.datasource.ClimateDataSource
import com.sngular.wheatherapp.domain.models.current.CurrentClimate
import com.sngular.wheatherapp.domain.models.forecast.ForecastClimate
import com.sngular.wheatherapp.domain.models.Location
import com.sngular.wheatherapp.domain.repository.ClimateRepository
import io.reactivex.Single

class ClimateRepositoryImpl(private val climateDataSource: ClimateDataSource) : ClimateRepository {

    override fun retrieveCurrentClimate(location: Location): Single<CurrentClimate> =
        climateDataSource.retrieveCurrentClimate(location.latitude, location.longitude)

    override fun retrieveForecastClimate(location: Location): Single<ForecastClimate> =
        climateDataSource.retrieveForecastClimate(location.latitude, location.longitude)

}