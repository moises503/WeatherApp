package com.sngular.wheatherapp.presentation.view.ui.forecast.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sngular.core.util.IconUtil
import com.sngular.core.util.inflate
import com.sngular.core.util.toDate
import com.sngular.core.util.toString
import com.sngular.wheatherapp.R
import com.sngular.wheatherapp.domain.models.forecast.WeatherDate
import kotlinx.android.synthetic.main.item_forecast_climate.view.*
import java.util.*

class ForecastClimateAdapter(private var weatherDates: List<WeatherDate> = emptyList()) :
    RecyclerView.Adapter<ForecastClimateAdapter.ForecastClimateViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastClimateViewHolder =
        ForecastClimateViewHolder(
            parent.inflate(R.layout.item_forecast_climate)
        )

    override fun getItemCount(): Int = weatherDates.size

    override fun onBindViewHolder(holder: ForecastClimateViewHolder, position: Int) =
        holder.bindViews(weatherDates[position])

    fun updateDataSet(weatherDates: List<WeatherDate>) {
        this.weatherDates = weatherDates
        this.notifyDataSetChanged()
    }

    class ForecastClimateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindViews(weatherDate: WeatherDate) {
            with(weatherDate) {
                itemView.txtDate.text = (date.toDate("yyyy-MM-dd HH:mm:ss")
                    ?: Date()).toString("EEE, d MMM yyyy HH:mm:ss")
                itemView.txtDescription.text = weather.first().description
                itemView.txtAverageTemperature.text = String.format(
                    itemView.context.getString(R.string.average_temperature),
                    temperature.current
                )
                itemView.txtMaxTemperature.text = String.format(
                    itemView.context.getString(R.string.max_temp),
                    temperature.max
                )
                itemView.txtMinTemperature.text = String.format(
                    itemView.context.getString(R.string.min_temp),
                    temperature.min
                )
                itemView.imgClimateAnimation.setAnimation(
                    IconUtil.getRightAnimationForCurrentWeather(
                        weather.first().icon
                    )
                )
                itemView.imgClimateAnimation.playAnimation()
            }
        }
    }
}