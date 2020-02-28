package com.sngular.wheatherapp.presentation.view.ui.weather

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import com.sngular.core.arch.ScreenState
import com.sngular.core.navigation.BaseFragment
import com.sngular.core.util.setToolbar
import com.sngular.core.util.toast
import com.sngular.wheatherapp.BuildConfig
import com.sngular.wheatherapp.R
import com.sngular.wheatherapp.domain.models.Location
import com.sngular.wheatherapp.domain.models.current.CurrentClimate
import com.sngular.wheatherapp.presentation.common.CurrentLocation
import com.sngular.wheatherapp.presentation.common.LocationLiveData
import com.sngular.wheatherapp.presentation.view.ui.MainActivity
import com.sngular.wheatherapp.presentation.view.ui.forecast.ForecastKey
import com.sngular.wheatherapp.presentation.viewmodel.ClimateState
import com.sngular.wheatherapp.presentation.viewmodel.ClimateViewModel
import kotlinx.android.synthetic.main.appbar_toolbar.*
import kotlinx.android.synthetic.main.fragment_current_weather.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CurrentWeatherFragment : BaseFragment() {

    private var lastLocation: CurrentLocation? = null
    private lateinit var currentCity: String
    private val climateViewModel: ClimateViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_current_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            (it as AppCompatActivity).setToolbar(
                toolbar,
                toolbar_title,
                getString(R.string.current_weather),
                false
            )
        }
        climateViewModel.locationLiveData = LocationLiveData(requireContext())
        pbSwipeMain.setOnRefreshListener {
            lastLocation?.let {
                climateViewModel.retrieveCurrentClimate(Location(it.latitude, it.longitude))
            }
        }
        btnShowForecast.setOnClickListener {
            context?.let {
                MainActivity[it].navigateTo(
                    ForecastKey(
                        currentCity,
                        lastLocation
                    )
                )
            }
        }
        checkIfHasPermissionsAndStartLocationTracking()
    }


    private fun updateUI(screenState: ScreenState<ClimateState>?) {
        when (screenState) {
            ScreenState.Loading -> showLoader()
            is ScreenState.Render -> processWeatherState(screenState.data)
        }
    }


    private fun processWeatherState(climateState: ClimateState) {
        hideLoader()
        when (climateState) {
            is ClimateState.SuccessClimate -> displayCurrentWeather(
                climateState.currentClimate,
                climateState.animation
            )
            is ClimateState.Error -> showError(climateState.error)
        }
    }

    private fun showLoader() {
        pbSwipeMain?.isRefreshing = true
    }

    private fun hideLoader() {
        pbSwipeMain?.isRefreshing = false
    }

    private fun showError(message: String) {
        context?.toast(message)
    }

    private fun displayCurrentWeather(currentClimate: CurrentClimate, animation: String) {
        lytCurrentWeather?.visibility = View.VISIBLE
        anCurrentWeather?.setAnimation(animation)
        anCurrentWeather?.playAnimation()
        currentCity = currentClimate.city
        txtCurrentLocation?.text = String
            .format(
                getString(R.string.show_current_wheather),
                currentCity,
                String.format(
                    getString(R.string.celsius_grades),
                    currentClimate.temperature.current
                )
            )
        txtDescription?.text = currentClimate.wheather.first().description
        val currentTemperature = currentClimate.temperature
        txtMinTemperature?.text = String
            .format(getString(R.string.min_temp), currentTemperature.min)
        txtMaxTemperature?.text = String
            .format(getString(R.string.max_temp), currentTemperature.max)
    }

    private fun checkIfHasPermissionsAndStartLocationTracking() {
        activity?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) Dexter.withActivity(it)
                .withPermissions(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
                )
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                        report?.let { multiple ->
                            if (multiple.deniedPermissionResponses.isEmpty()) {
                                showLoader()
                                startTracking()
                            } else {
                                openSettings()
                            }
                        }

                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permissions: MutableList<PermissionRequest>?,
                        token: PermissionToken?
                    ) {
                        showPermissionRationale(token, it)
                    }
                }).check() else {
                Dexter.withActivity(it)
                    .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    .withListener(object : PermissionListener {
                        override fun onPermissionGranted(response: PermissionGrantedResponse) {
                            showLoader()
                            startTracking()
                        }

                        override fun onPermissionDenied(response: PermissionDeniedResponse) {
                            if (response.isPermanentlyDenied) {
                                openSettings()
                            }
                        }

                        override fun onPermissionRationaleShouldBeShown(
                            permission: PermissionRequest,
                            token: PermissionToken
                        ) {
                            token.continuePermissionRequest()
                        }
                    }).check()
            }
        }
    }

    private fun openSettings() {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
        intent.data = uri
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }


    private fun showPermissionRationale(token: PermissionToken?, context: Context) {
        AlertDialog.Builder(context).setTitle(R.string.permission_rationale_title)
            .setMessage(R.string.permission_rationale_message)
            .setNegativeButton(
                android.R.string.cancel
            ) { dialog, _ ->
                dialog.dismiss()
                token?.cancelPermissionRequest()
            }
            .setPositiveButton(
                android.R.string.ok
            ) { dialog, _ ->
                dialog.dismiss()
                token?.continuePermissionRequest()
            }
            .setOnDismissListener { token?.cancelPermissionRequest() }
            .show()
    }

    fun startTracking() {
        climateViewModel.locationLiveData?.observe(viewLifecycleOwner, Observer { location ->
            lastLocation = location
            climateViewModel.climateState(
                Location(location.latitude, location.longitude)
            ).observe(viewLifecycleOwner, Observer {
                updateUI(it)
            })
        })
    }
}
