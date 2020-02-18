package com.sngular.wheatherapp

import com.nhaarman.mockitokotlin2.*
import com.sngular.wheatherapp.domain.models.Location
import com.sngular.wheatherapp.domain.models.current.CurrentClimate
import com.sngular.wheatherapp.domain.models.forecast.ForecastClimate
import com.sngular.wheatherapp.presentation.presenter.ClimateContract
import com.sngular.wheatherapp.seeds.ClimateSeeder
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.junit.MockitoJUnit

class ClimatePresenterTest {
    @Rule
    @JvmField
    val rule = MockitoJUnit.rule()!!

    @Rule
    @JvmField var testSchedulerRule = RxImmediateSchedulerRule()

    private lateinit var location: Location
    private lateinit var currentClimate: CurrentClimate
    private lateinit var forecastClimate: ForecastClimate
    private lateinit var error : String
    private lateinit var climatePresenter : ClimateContract.Presenter
    private lateinit var climateView : ClimateContract.BaseView

    @Before
    fun setup() {
        location = ClimateSeeder.retrieveLocationClimateFakeData()
        currentClimate = ClimateSeeder.retrieveCurrentWeather()
        forecastClimate = ClimateSeeder.retrieveForecastClimate()
        error = "This is an error"
        climatePresenter = mock()
        climateView = mock()
    }

    @Test
    fun whenTryToGetCurrentClimateReturnsACurrentClimateObject() {
        whenever(climatePresenter.retrieveCurrentClimate(location)).then {
            climateView.displayCurrentWeather(currentClimate, "02d")
        }
        climatePresenter.retrieveCurrentClimate(location)
        verify(climateView, times(1)).displayCurrentWeather(currentClimate, "02d")
        verify(climateView, never()).showError(error)
    }

    @Test
    fun whenTryToGetForecastClimateReturnsAForecastClimateObject() {
        whenever(climatePresenter.retrieveForecastClimate(location)).then {
            climateView.displayForecastClimate(forecastClimate)
        }
        climatePresenter.retrieveForecastClimate(location)
        verify(climateView, times(1)).displayForecastClimate(forecastClimate)
        verify(climateView, never()).showError(error)
    }

    @Test
    fun whenTryToGetForecastClimateAndThrowErrorShowThatError() {
        whenever(climatePresenter.retrieveForecastClimate(location)).then {
            climateView.showError(error)
        }
        climatePresenter.retrieveForecastClimate(location)
        verify(climateView, never()).displayForecastClimate(forecastClimate)
        verify(climateView, times(1)).showError(error)
    }

    @Test
    fun whenTryToGetCurrentClimateAndThrowErrorShowThatError() {
        whenever(climatePresenter.retrieveCurrentClimate(location)).then {
            climateView.showError(error)
        }
        climatePresenter.retrieveCurrentClimate(location)
        verify(climateView, never()).displayCurrentWeather(currentClimate, "02d")
        verify(climateView, times(1)).showError(error)
    }
}