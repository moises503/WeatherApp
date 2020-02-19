package com.sngular.wheatherapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sngular.core.arch.BaseViewModel
import com.sngular.core.arch.Observer
import com.sngular.core.arch.ScreenState
import com.sngular.core.util.IconUtil
import com.sngular.wheatherapp.domain.models.Location
import com.sngular.wheatherapp.domain.models.current.CurrentClimate
import com.sngular.wheatherapp.domain.usecase.CurrentClimateUseCase
import com.sngular.wheatherapp.presentation.presenter.ClimateContract

class ClimateViewModel(
    private val currentClimateUseCase: CurrentClimateUseCase,
    private val stringResources: ClimateContract.StringResources
) : BaseViewModel() {

    private lateinit var _climateState : MutableLiveData<ScreenState<ClimateState>>

    val climateState: LiveData<ScreenState<ClimateState>>
        get() {
            if (!::_climateState.isInitialized) {
                _climateState = MutableLiveData()
            }
            return _climateState
        }

    fun climateState(location: Location?) : LiveData<ScreenState<ClimateState>>{
        if(!::_climateState.isInitialized){
            _climateState = MutableLiveData()
            retrieveCurrentClimate(location ?: Location(0.0, 0.0))
        }
        return _climateState
    }

    fun retrieveCurrentClimate(location: Location) {
        _climateState.value = ScreenState.Loading
        addDisposable(
            currentClimateUseCase.executeAndReturnDisposable(location, CurrentClimateObserver())
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
}