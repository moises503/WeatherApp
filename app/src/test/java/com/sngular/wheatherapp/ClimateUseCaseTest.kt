package com.sngular.wheatherapp

import com.nhaarman.mockitokotlin2.*
import com.sngular.core.arch.Observer
import com.sngular.core.arch.SingleUseCase
import com.sngular.wheatherapp.domain.models.Location
import com.sngular.wheatherapp.domain.models.current.CurrentClimate
import com.sngular.wheatherapp.domain.models.forecast.ForecastClimate
import com.sngular.wheatherapp.seeds.ClimateSeeder
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.junit.MockitoJUnit

class ClimateUseCaseTest {


    @Rule
    @JvmField
    val rule = MockitoJUnit.rule()!!

    @Rule
    @JvmField var testSchedulerRule = RxImmediateSchedulerRule()

    private lateinit var currentClimateUseCase : SingleUseCase<Location, CurrentClimate>
    private lateinit var forecastClimateUseCase : SingleUseCase<Location, ForecastClimate>
    private lateinit var currentClimateObserver : Observer<CurrentClimate>
    private lateinit var forecastClimateObserver: Observer<ForecastClimate>
    private lateinit var currentClimate: CurrentClimate
    private lateinit var forecastClimate: ForecastClimate
    private lateinit var location: Location

    @Before
    fun setup() {
        currentClimateUseCase = mock()
        currentClimateObserver = mock()
        forecastClimateUseCase = mock()
        forecastClimateObserver = mock()
        currentClimate = ClimateSeeder.retrieveCurrentWeather()
        forecastClimate = ClimateSeeder.retrieveForecastClimate()
        location = ClimateSeeder.retrieveLocationClimateFakeData()
    }

    @Test
    fun whenExecutesCurrentClimateUseCaseAlwaysExecuteOnSuccessMethod() {
        whenever(currentClimateUseCase.execute(location, currentClimateObserver)).then{
            currentClimateObserver.onSuccess(currentClimate)
        }
        currentClimateUseCase.execute(location, currentClimateObserver)
        verify(currentClimateObserver, times(1)).onSuccess(currentClimate)
        verify(currentClimateObserver, never()).onError(any())
    }

    @Test
    fun whenExecutesForestClimateUseCaseAlwaysExecuteOnSuccessMethod() {
        whenever(forecastClimateUseCase.execute(location, forecastClimateObserver)).then{
            forecastClimateObserver.onSuccess(forecastClimate)
        }
        forecastClimateUseCase.execute(location, forecastClimateObserver)
        verify(forecastClimateObserver, times(1)).onSuccess(forecastClimate)
        verify(forecastClimateObserver, never()).onError(any())
    }

    @Test(expected = Exception::class)
    fun whenSendNullParametersCurrentClimateUseCaseThrowsAnException(){
        doAnswer { throw Exception("This is an error") }
            .`when`(currentClimateUseCase).execute(null, currentClimateObserver)
        currentClimateUseCase.execute(null, currentClimateObserver)
    }

    @Test(expected = Exception::class)
    fun whenSendNullParametersForestClimateUseCaseThrowsAnException(){
        doAnswer { throw Exception("This is an error") }
            .`when`(forecastClimateUseCase).execute(null, forecastClimateObserver)
        forecastClimateUseCase.execute(null, forecastClimateObserver)
    }
}