package com.sngular.wheatherapp.presentation.view.ui.forecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sngular.core.arch.ScreenState
import com.sngular.core.navigation.BaseFragment
import com.sngular.core.util.setToolbar
import com.sngular.core.util.toast
import com.sngular.wheatherapp.R
import com.sngular.wheatherapp.domain.models.forecast.ForecastClimate
import com.sngular.wheatherapp.presentation.common.CurrentLocation
import com.sngular.wheatherapp.presentation.view.ui.forecast.adapter.ForecastClimateAdapter
import com.sngular.wheatherapp.presentation.viewmodel.ClimateState
import com.sngular.wheatherapp.presentation.viewmodel.ForecastViewModel
import kotlinx.android.synthetic.main.appbar_toolbar.*
import kotlinx.android.synthetic.main.fragment_forecast.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class ForecastFragment : BaseFragment() {

    private var currentCity = ""
    private var lastLocation: CurrentLocation? = null
    private lateinit var forecastClimateAdapter: ForecastClimateAdapter
    private val forecastViewModel: ForecastViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentCity = getKey<ForecastKey>()?.city ?: ""
        lastLocation = getKey<ForecastKey>()?.location
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_forecast, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            (it as AppCompatActivity).setToolbar(
                toolbar,
                toolbar_title,
                getString(R.string.forecast),
                true
            )
        }
        lastLocation?.let {
            forecastViewModel.forecastState(com.sngular.wheatherapp.domain.models.Location(
                it.latitude,
                it.longitude
            )).observe(viewLifecycleOwner, Observer { forecast ->
                updateUI(forecast)
            })
        }
        txtForecastCity?.text = String.format(getString(R.string.each_for), currentCity)
        pbSwipeForecast.setOnRefreshListener {
            lastLocation?.let {
                forecastViewModel.retrieveForecastClimate(
                    com.sngular.wheatherapp.domain.models.Location(
                        it.latitude,
                        it.longitude
                    )
                )
            }
        }
        forecastClimateAdapter =
            ForecastClimateAdapter()
        lstForecast?.apply {
            adapter = forecastClimateAdapter
            layoutManager = LinearLayoutManager(context)
            layoutAnimation = AnimationUtils.loadLayoutAnimation(this.context, R.anim.layout_animation_from_right)
        }
    }

    private fun updateUI(screenState: ScreenState<ClimateState>?) {
        when (screenState) {
            ScreenState.Loading -> showLoader()
            is ScreenState.Render -> processForecastState(screenState.data)
        }
    }

    private fun processForecastState(forecastState : ClimateState) {
        hideLoader()
        when(forecastState) {
            is ClimateState.SuccessForeCast -> displayForecastClimate(forecastState.forecastClimate)
            is ClimateState.Error -> showError(forecastState.error)
        }
    }

    private fun showLoader() {
        pbSwipeForecast?.isRefreshing = true
    }

    private fun hideLoader() {
        pbSwipeForecast?.isRefreshing = false
    }

    private fun showError(message: String) {
        context?.toast(message)
    }

    private fun displayForecastClimate(forecastClimate: ForecastClimate) {
        forecastClimateAdapter.updateDataSet(forecastClimate.weatherDates)
        lstForecast.scheduleLayoutAnimation()
    }
}
