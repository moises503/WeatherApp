package com.sngular.wheatherapp

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.sngular.wheatherapp.domain.models.Location
import com.sngular.wheatherapp.domain.models.current.CurrentClimate
import com.sngular.wheatherapp.domain.models.forecast.ForecastClimate
import com.sngular.wheatherapp.domain.repository.ClimateRepository
import com.sngular.wheatherapp.seeds.ClimateSeeder
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.mockito.junit.MockitoJUnit
import org.junit.Assert
import org.junit.Test


class ClimateRepositoryTest {
    @Rule
    @JvmField
    val rule = MockitoJUnit.rule()!!

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    private lateinit var currentClimate: CurrentClimate
    private lateinit var forecastClimate: ForecastClimate
    private lateinit var singleCurrentClimate: Single<CurrentClimate>
    private lateinit var singleForecastClimate: Single<ForecastClimate>
    private lateinit var climateRepository: ClimateRepository
    private lateinit var location: Location

    @Before
    fun setup() {
        location = ClimateSeeder.retrieveLocationClimateFakeData()
        currentClimate = ClimateSeeder.retrieveCurrentWeather()
        forecastClimate = ClimateSeeder.retrieveForecastClimate()
        singleCurrentClimate = Single.just(currentClimate)
        singleForecastClimate = Single.just(forecastClimate)
        climateRepository = mock()
    }

    @Test
    fun whenExecuteRetrieveCurrentClimateAlwaysReturnCurrentClimateObject() {
        whenever(climateRepository.retrieveCurrentClimate(location)).thenReturn(singleCurrentClimate)
        val result = climateRepository.retrieveCurrentClimate(location)
        Assert.assertEquals(result, singleCurrentClimate)
    }

    @Test
    fun whenExecuteRetrieveForecastClimateAlwaysReturnForecastClimateObject() {
        whenever(climateRepository.retrieveForecastClimate(location)).thenReturn(singleForecastClimate)
        val result = climateRepository.retrieveForecastClimate(location)
        Assert.assertEquals(result, singleForecastClimate)
    }

    @Test
    fun whenExecuteRepositoryCurrentClimateAlwaysReturnOnSuccess() {
        whenever(climateRepository.retrieveCurrentClimate(location)).thenReturn(singleCurrentClimate)
        val currentClimateTestObserver = climateRepository.retrieveCurrentClimate(location).test()
        currentClimateTestObserver.assertComplete()
        currentClimateTestObserver.assertNoErrors()
    }

    @Test
    fun whenExecuteRepositoryForecastClimateAlwaysReturnOnSuccess() {
        whenever(climateRepository.retrieveForecastClimate(location)).thenReturn(singleForecastClimate)
        val currentForecastObservable = climateRepository.retrieveForecastClimate(location).test()
        currentForecastObservable.assertComplete()
        currentForecastObservable.assertNoErrors()
    }
}