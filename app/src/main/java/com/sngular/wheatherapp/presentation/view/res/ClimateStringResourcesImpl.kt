package com.sngular.wheatherapp.presentation.view.res

import android.content.Context
import com.sngular.wheatherapp.R
import com.sngular.wheatherapp.presentation.ClimateContract

class ClimateStringResourcesImpl(private val context: Context) : ClimateContract.StringResources {

    override fun currentClimateErrorMessage(): String {
        return context.getString(R.string.current_climate_error_message)
    }

    override fun forecastClimateErrorMessage(): String {
        return context.getString(R.string.forecast_climate_error_message)
    }

}