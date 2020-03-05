package com.sngular.wheatherapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sngular.core.arch.BaseViewModel
import com.sngular.core.arch.Observer
import com.sngular.core.arch.ScreenState
import com.sngular.wheatherapp.domain.models.Location
import com.sngular.wheatherapp.domain.models.forecast.ForecastClimate
import com.sngular.wheatherapp.domain.usecase.ForecastClimateUseCase
import com.sngular.wheatherapp.presentation.presenter.ClimateContract

class ForecastViewModel(
    private val forecastClimateUseCase: ForecastClimateUseCase,
    private val stringResources: ClimateContract.StringResources
) : BaseViewModel() {

    private lateinit var _forecastState: MutableLiveData<ScreenState<ClimateState>>

    private val forecastStateMap: Map<Location, LiveData<ScreenState<ClimateState>>> =
        lazyMap { parameters ->
            _forecastState = MutableLiveData()
            retrieveForecastClimate(parameters)
            return@lazyMap _forecastState
        }

    fun forecastStateView(parameters: Location): LiveData<ScreenState<ClimateState>> =
        forecastStateMap.getValue(parameters)


    fun retrieveForecastClimate(location: Location) {
        _forecastState.value = ScreenState.Loading
        addDisposable(
            forecastClimateUseCase.executeAndReturnDisposable(
                location,
                ForecastClimateObserver()
            )
        )
    }

    private inner class ForecastClimateObserver : Observer<ForecastClimate>() {

        override fun onSuccess(t: ForecastClimate) {
            _forecastState.value = ScreenState.Render(
                ClimateState.SuccessForeCast(
                    t
                )
            )
        }

        override fun onError(e: Throwable) {
            _forecastState.value = ScreenState.Render(
                ClimateState.Error(
                    e.message ?: stringResources.forecastClimateErrorMessage()
                )
            )
        }

    }
}

fun <K, V> lazyMap(initializer: (K) -> V): Map<K, V> {
    val map = mutableMapOf<K, V>()
    return map.withDefault { key ->
        val newValue = initializer(key)
        map[key] = newValue
        return@withDefault newValue
    }
}