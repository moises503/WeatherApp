package com.sngular.wheatherapp.data.datasource

import com.sngular.core.util.Constants
import com.sngular.wheatherapp.data.mapper.current.CurrentWeatherResponseToCurrentWeather
import com.sngular.wheatherapp.data.mapper.forecast.ForecastResponseToForecastClimate
import com.sngular.wheatherapp.domain.datasource.ClimateDataSource
import com.sngular.wheatherapp.domain.exceptions.CurrentWeatherNotFoundException
import com.sngular.wheatherapp.domain.exceptions.ForecastClimateNotFoundException
import com.sngular.wheatherapp.domain.models.current.CurrentClimate
import com.sngular.wheatherapp.domain.models.forecast.ForecastClimate
import io.reactivex.Single

class ClimateDataSourceImpl(
    private val endPoint: EndPoint,
    private val currentWeatherResponseToCurrentWeather: CurrentWeatherResponseToCurrentWeather,
    private val forecastResponseToForecastClimate: ForecastResponseToForecastClimate
) : ClimateDataSource {

    override fun retrieveCurrentClimate(
        latitude: Double,
        longitude: Double
    ): Single<CurrentClimate> {
        return endPoint.attemptToGetCurrentClimate(
            latitude,
            longitude,
            Constants.UNITS,
            Constants.API_KEY
        )
            .onErrorResumeNext { error ->
                return@onErrorResumeNext Single.error(error)
            }.map { response ->
                when {
                    response.cod == 200 ->
                        return@map currentWeatherResponseToCurrentWeather.transform(response)
                    else ->
                        throw CurrentWeatherNotFoundException()
                }
            }
    }

    override fun retrieveForecastClimate(
        latitude: Double,
        longitude: Double
    ): Single<ForecastClimate> {
        return endPoint.attemptToGetForecast(
            latitude,
            longitude,
            Constants.UNITS,
            Constants.API_KEY
        )
            .onErrorResumeNext { error ->
                return@onErrorResumeNext Single.error(error)
            }.map { response ->
                when {
                    response.cod == "200" ->
                        return@map forecastResponseToForecastClimate.transform(response)
                    else ->
                        throw ForecastClimateNotFoundException()
                }
            }
    }
}