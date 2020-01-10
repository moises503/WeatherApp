package com.sngular.wheatherapp

import com.sngular.wheatherapp.domain.models.Location
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.sngular.wheatherapp.data.mapper.current.CurrentWeatherResponseToCurrentWeather
import com.sngular.wheatherapp.data.mapper.current.MainTemperatureToTemperature
import com.sngular.wheatherapp.data.mapper.current.WeatherListResponseToWeatherList
import com.sngular.wheatherapp.data.mapper.forecast.ForecastClimateToWeatherDate
import com.sngular.wheatherapp.data.mapper.forecast.ForecastResponseToForecastClimate
import com.sngular.wheatherapp.data.models.current.CurrentClimateResponse
import com.sngular.wheatherapp.domain.models.forecast.ForecastClimate
import com.sngular.wheatherapp.data.models.forecast.ForecastResponse
import com.sngular.wheatherapp.domain.datasource.ClimateDataSource
import com.sngular.wheatherapp.domain.models.current.CurrentClimate
import com.sngular.wheatherapp.seeds.ClimateSeeder
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.junit.MockitoJUnit
import org.junit.Assert


class ClimateDataSourceTest {

    @Rule
    @JvmField
    val rule = MockitoJUnit.rule()!!

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    private lateinit var forecastResponse: ForecastResponse
    private lateinit var currentClimateResponse: CurrentClimateResponse
    private lateinit var currentClimate: Single<CurrentClimate>
    private lateinit var forecastClimate: Single<ForecastClimate>
    private lateinit var location: Location
    private lateinit var currentWeatherResponseToCurrentWeather: CurrentWeatherResponseToCurrentWeather
    private lateinit var mainTemperatureToTemperature: MainTemperatureToTemperature
    private lateinit var weatherListResponseToWeatherList: WeatherListResponseToWeatherList
    private lateinit var forecastResponseToForecastClimate: ForecastResponseToForecastClimate
    private lateinit var forecastClimateToWeatherDate: ForecastClimateToWeatherDate
    private lateinit var climateDataSource: ClimateDataSource

    @Before
    fun setup() {
        forecastResponse = ClimateSeeder.retrieveForecastClimateFakeData()
        currentClimateResponse = ClimateSeeder.retrieveCurrentClimateFakerData()
        location = ClimateSeeder.retrieveLocationClimateFakeData()
        mainTemperatureToTemperature = MainTemperatureToTemperature()
        weatherListResponseToWeatherList = WeatherListResponseToWeatherList()
        forecastClimateToWeatherDate = ForecastClimateToWeatherDate(
            weatherListResponseToWeatherList,
            mainTemperatureToTemperature
        )
        forecastResponseToForecastClimate =
            ForecastResponseToForecastClimate(forecastClimateToWeatherDate)
        currentWeatherResponseToCurrentWeather =
            CurrentWeatherResponseToCurrentWeather(
                mainTemperatureToTemperature = mainTemperatureToTemperature,
                weatherListResponseToWeatherList = weatherListResponseToWeatherList
            )
        currentClimate = Single.just(currentClimateResponse).map {
            currentWeatherResponseToCurrentWeather.transform(it)
        }
        forecastClimate = Single.just(forecastResponse).map {
            forecastResponseToForecastClimate.transform(it)
        }
        climateDataSource = mock()
    }

    @Test
    fun whenAttemptToGetCurrentWeatherReturnsCurrentClimateModelObject() {
        whenever(
            climateDataSource.retrieveCurrentClimate(
                location.latitude,
                location.longitude
            )
        ).thenReturn(currentClimate)
        val result = climateDataSource.retrieveCurrentClimate(
            location.latitude,
            location.longitude
        )
        Assert.assertEquals(result, currentClimate)
    }

    @Test
    fun whenAttemptToRetrieveForecastReturnsForecastClimateObject() {
        whenever(
            climateDataSource.retrieveForecastClimate(
                location.latitude,
                location.longitude
            )
        ).thenReturn(forecastClimate)
        val result = climateDataSource.retrieveForecastClimate(
            location.latitude,
            location.longitude
        )
        Assert.assertEquals(result, forecastClimate)
    }
}
