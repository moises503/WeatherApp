package com.sngular.wheatherapp.data.datasource

import com.sngular.wheatherapp.data.models.current.CurrentClimateResponse
import com.sngular.wheatherapp.data.models.forecast.ForecastResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface EndPoint {
    @GET("weather")
    fun attemptToGetCurrentClimate(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") units : String,
        @Query("APPID") appId: String
    ) : Single<CurrentClimateResponse>

    @GET("forecast")
    fun attemptToGetForecast(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") units : String,
        @Query("APPID") appId: String
    ) : Single<ForecastResponse>
}