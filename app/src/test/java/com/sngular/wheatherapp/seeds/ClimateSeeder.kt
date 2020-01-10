package com.sngular.wheatherapp.seeds

import com.sngular.wheatherapp.domain.models.Location
import com.sngular.wheatherapp.data.models.current.CurrentClimateResponse
import com.sngular.wheatherapp.data.models.current.Main
import com.sngular.wheatherapp.data.models.current.WeatherItem
import com.sngular.wheatherapp.data.models.forecast.ForecastClimate
import com.sngular.wheatherapp.data.models.forecast.ForecastResponse
import com.sngular.wheatherapp.domain.models.current.CurrentClimate
import com.sngular.wheatherapp.domain.models.current.Temperature
import com.sngular.wheatherapp.domain.models.current.Weather
import com.sngular.wheatherapp.domain.models.forecast.WeatherDate

class ClimateSeeder {
    companion object {
        fun retrieveCurrentClimateFakerData(): CurrentClimateResponse {
            return CurrentClimateResponse(
                visibility = 1000,
                timezone = 1000,
                dt = 1000,
                cod = 200,
                id = 100,
                base = "1000",
                name = "Oaxaca",
                main = Main(
                    temp = 1000.0, tempMax = 1000.0, tempMin = 1000.0, humidity = 1000,
                    pressure = 1000, feelsLike = 1000.0
                ),
                weather = listOf(
                    WeatherItem(
                        icon = "01d",
                        description = "windy",
                        main = "windy",
                        id = 1000
                    )
                )
            )
        }

        fun retrieveForecastClimateFakeData(): ForecastResponse {
            return ForecastResponse(
                list = listOf(
                    ForecastClimate(
                        dt = 1000, dtTxt = "2020-01-07 09:00:00", main = Main(
                            temp = 1000.0, tempMax = 1000.0, tempMin = 1000.0, humidity = 1000,
                            pressure = 1000, feelsLike = 1000.0
                        ),
                        weather = listOf(
                            WeatherItem(
                                icon = "01d",
                                description = "windy",
                                main = "windy",
                                id = 1000
                            )
                        )
                    )
                ), cod = "200"
            )
        }

        fun retrieveLocationClimateFakeData(): Location {
            return Location(latitude = 102.000, longitude = 32.9292)
        }

        fun retrieveForecastClimate(): com.sngular.wheatherapp.domain.models.forecast.ForecastClimate {
            return com.sngular.wheatherapp.domain.models.forecast.ForecastClimate(
                weatherDates = listOf(
                    WeatherDate(
                        weather = listOf(
                            Weather(
                                id = 10101,
                                main = "lol",
                                description = "lol",
                                icon = "02d"
                            )
                        ),
                        temperature = Temperature(current = "200", max = "200", min = "200"),
                        date = "2020-01-06 12:00:00"
                    )
                )
            )
        }

        fun retrieveCurrentWeather(): CurrentClimate {
            return CurrentClimate(
                city = "Oaxaca",
                wheather = listOf(
                    Weather(
                        id = 10101,
                        main = "lol",
                        description = "lol",
                        icon = "02d"
                    )
                ),
                temperature = Temperature(current = "200", max = "200", min = "200")
            )
        }
    }
}