package com.sngular.wheatherapp.presentation.presenter

import com.sngular.core.arch.BasePresenter
import com.sngular.core.arch.Observer
import com.sngular.core.util.IconUtil
import com.sngular.wheatherapp.domain.models.forecast.ForecastClimate
import com.sngular.wheatherapp.domain.models.current.CurrentClimate
import com.sngular.wheatherapp.domain.models.Location
import com.sngular.wheatherapp.domain.provider.ClimateUseCaseProvider

class ClimatePresenterImpl(
    private val climateUseCaseProvider: ClimateUseCaseProvider,
    private val view: ClimateContract.BaseView,
    private val stringResources : ClimateContract.StringResources
) : ClimateContract.Presenter, BasePresenter() {

    override fun retrieveCurrentClimate(location: Location) {
        view.showLoader()
        addDisposable(
            climateUseCaseProvider.providesCurrentWeatherUseCase()
                .executeAndReturnDisposable(location, CurrentClimateObserver())
        )
    }

    override fun retrieveForecastClimate(location: Location) {
        view.showLoader()
        addDisposable(
            climateUseCaseProvider.providesForecastClimate().executeAndReturnDisposable(
                location,
                ForecastClimateObserver()
            )
        )
    }

    override fun onStop() {
        destroy()
    }

    private inner class CurrentClimateObserver : Observer<CurrentClimate>() {
        override fun onSuccess(t: CurrentClimate) {
            view.hideLoader()
            view.displayCurrentWeather(
                t,
                IconUtil.getRightAnimationForCurrentWeather(t.wheather.first().icon)
            )
        }

        override fun onError(e: Throwable) {
            view.hideLoader()
            view.showError(e.message ?: stringResources.currentClimateErrorMessage())
        }
    }

    private inner class ForecastClimateObserver : Observer<ForecastClimate>() {

        override fun onSuccess(t: ForecastClimate) {
            view.hideLoader()
            view.displayForecastClimate(t)
        }

        override fun onError(e: Throwable) {
            view.hideLoader()
            view.showError(e.message ?: stringResources.forecastClimateErrorMessage())
        }

    }

}