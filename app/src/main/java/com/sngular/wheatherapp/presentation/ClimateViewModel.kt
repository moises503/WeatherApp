package com.sngular.wheatherapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sngular.core.arch.BaseViewModel
import com.sngular.core.arch.Observer
import com.sngular.core.arch.ScreenState
import com.sngular.core.util.IconUtil
import com.sngular.wheatherapp.domain.models.Location
import com.sngular.wheatherapp.domain.models.current.CurrentClimate
import com.sngular.wheatherapp.domain.models.forecast.ForecastClimate
import com.sngular.wheatherapp.domain.provider.ClimateUseCaseProvider

class ClimateViewModel(
    private val climateUseCaseProvider: ClimateUseCaseProvider,
    private val stringResources: ClimateContract.StringResources
) : BaseViewModel() {

    private val _climateState = MutableLiveData<ScreenState<ClimateState>>()

    val climateState: LiveData<ScreenState<ClimateState>>
        get() = _climateState

    fun retrieveCurrentClimate(location: Location) {
        _climateState.value = ScreenState.Loading
        addDisposable(
            climateUseCaseProvider.providesCurrentWeatherUseCase()
                .executeAndReturnDisposable(location, CurrentClimateObserver())
        )
    }

    fun retrieveForecastClimate(location: Location) {
        _climateState.value = ScreenState.Loading
        addDisposable(
            climateUseCaseProvider.providesForecastClimate().executeAndReturnDisposable(
                location,
                ForecastClimateObserver()
            )
        )
    }

    private inner class CurrentClimateObserver : Observer<CurrentClimate>() {
        override fun onSuccess(t: CurrentClimate) {
            _climateState.value = ScreenState.Render(
                ClimateState.SuccessClimate(
                    t,
                    IconUtil.getRightAnimationForCurrentWeather(t.wheather.first().icon)
                )
            )
        }

        override fun onError(e: Throwable) {
            _climateState.value = ScreenState.Render(
                ClimateState.Error(
                    e.message ?: stringResources.currentClimateErrorMessage()
                )
            )
        }
    }

    private inner class ForecastClimateObserver : Observer<ForecastClimate>() {

        override fun onSuccess(t: ForecastClimate) {
            _climateState.value = ScreenState.Render(ClimateState.SuccessForeCast(t))
        }

        override fun onError(e: Throwable) {
            _climateState.value = ScreenState.Render(
                ClimateState.Error(
                    e.message ?: stringResources.forecastClimateErrorMessage()
                )
            )
        }

    }
}