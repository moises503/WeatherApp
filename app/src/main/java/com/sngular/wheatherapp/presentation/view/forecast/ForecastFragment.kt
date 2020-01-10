package com.sngular.wheatherapp.presentation.view.forecast


import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sngular.core.navigation.BaseFragment
import com.sngular.core.util.setToolbar
import com.sngular.core.util.toast

import com.sngular.wheatherapp.R
import com.sngular.wheatherapp.domain.models.forecast.ForecastClimate
import com.sngular.wheatherapp.presentation.ClimateContract
import com.sngular.wheatherapp.presentation.view.forecast.adapter.ForecastClimateAdapter
import kotlinx.android.synthetic.main.appbar_toolbar.*
import kotlinx.android.synthetic.main.fragment_forecast.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf



class ForecastFragment : BaseFragment(), ClimateContract.ForecastClimateView {

    private var currentCity = ""
    private var lastLocation: Location? = null
    private val climatePresenter: ClimateContract.Presenter
            by inject { parametersOf(this) }
    private lateinit var forecastClimateAdapter: ForecastClimateAdapter

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
            climatePresenter.retrieveForecastClimate(
                com.sngular.wheatherapp.domain.models.Location(
                    it.latitude,
                    it.longitude
                )
            )
        }
        txtForecastCity?.text = String.format(getString(R.string.each_for), currentCity)
        pbSwipeForecast.setOnRefreshListener {
            lastLocation?.let {
                climatePresenter.retrieveForecastClimate(
                    com.sngular.wheatherapp.domain.models.Location(
                        it.latitude,
                        it.longitude
                    )
                )
            }
        }
        forecastClimateAdapter = ForecastClimateAdapter()
        lstForecast?.apply {
            adapter = forecastClimateAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun showLoader() {
        pbSwipeForecast?.isRefreshing = true
    }

    override fun hideLoader() {
        pbSwipeForecast?.isRefreshing = false
    }

    override fun showError(message: String) {
        context?.toast(message)
    }

    override fun displayForecastClimate(forecastClimate: ForecastClimate) {
        val context = lstForecast.context
        val controller =
            AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_from_right)
        lstForecast?.layoutAnimation = controller
        forecastClimateAdapter.updateDataSet(forecastClimate.weatherDates)
        lstForecast.scheduleLayoutAnimation()
    }
}
