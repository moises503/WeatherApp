package com.sngular.wheatherapp.presentation.view.ui.forecast.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.airbnb.lottie.LottieAnimationView
import com.sngular.core.util.IconUtil
import com.sngular.core.util.toDate
import com.sngular.core.util.toString
import com.sngular.wheatherapp.R
import com.sngular.wheatherapp.domain.models.current.Weather
import java.util.*

@BindingAdapter("loadAnimation")
fun loadWeatherAnimation(animation: LottieAnimationView, weather: List<Weather>) {
    animation.setAnimation(
        IconUtil.getRightAnimationForCurrentWeather(
            weather.first().icon
        )
    )
    animation.playAnimation()
}

@BindingAdapter("loadDescription")
fun loadWeatherAnimation(textView: TextView, weather: List<Weather>) {
    textView.text = weather.first().description
}


@BindingAdapter("formatText")
fun formatTemperature(textView: TextView, temperature: String) {
    textView.text = String.format(
        textView.context.getString(R.string.average_temperature),
        temperature
    )
}

@BindingAdapter("loadDate")
fun loadDate(textView: TextView, date: String) {
    textView.text = (date.toDate("yyyy-MM-dd HH:mm:ss")
        ?: Date()).toString("EEE, d MMM yyyy HH:mm:ss")
}