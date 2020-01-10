package com.sngular.core.util

class IconUtil {
    companion object {
        fun getRightAnimationForCurrentWeather(icon: String): String {
            when (icon) {
                "01d", "01n" ->
                    return "sunny.json"
                "02d", "02n", "03d", "03n", "04d", "04n" ->
                    return "few_clouds.json"
                "09d", "09n", "10d", "10n" ->
                    return "rain.json"
                "11d", "11n" ->
                    return "thunderstorm.json"
                "13d", "13n" ->
                    return "snow.json"
                "50d", "50n" ->
                    return "mist.json"
                else ->
                    return "not_found.json"
            }
        }
    }
}