package com.sngular.wheatherapp.presentation.view.ui.weather.location

import android.location.Location
import android.os.Bundle
import android.util.Log

class LocationListener(provider: String, private val currentLocation: (location: Location) -> Unit) :
    android.location.LocationListener {

    private var lastLocation: Location? = null

    init {
        lastLocation = Location(provider)
    }

    override fun onLocationChanged(location: Location) {
        lastLocation = location
        currentLocation(lastLocation ?: location)
        Log.i(TAG, "LocationChanged: $location")
    }

    override fun onProviderDisabled(provider: String) {
        Log.e(TAG, "onProviderDisabled: $provider")
    }

    override fun onProviderEnabled(provider: String) {
        Log.e(TAG, "onProviderEnabled: $provider")
    }

    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
        Log.e(TAG, "onStatusChanged: $status")
    }

    companion object {
        private val TAG = LocationListener::class.java.simpleName
    }
}