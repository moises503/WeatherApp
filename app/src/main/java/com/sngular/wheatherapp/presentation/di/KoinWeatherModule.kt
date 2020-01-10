package com.sngular.wheatherapp.presentation.di

import android.content.Context
import com.sngular.core.arch.threads.JobScheduler
import com.sngular.core.arch.threads.UIScheduler
import com.sngular.wheatherapp.data.datasource.ClimateDataSourceImpl
import com.sngular.wheatherapp.data.datasource.EndPoint
import com.sngular.wheatherapp.data.mapper.current.CurrentWeatherResponseToCurrentWeather
import com.sngular.wheatherapp.data.mapper.current.MainTemperatureToTemperature
import com.sngular.wheatherapp.data.mapper.current.WeatherListResponseToWeatherList
import com.sngular.wheatherapp.data.mapper.forecast.ForecastClimateToWeatherDate
import com.sngular.wheatherapp.data.mapper.forecast.ForecastResponseToForecastClimate
import com.sngular.wheatherapp.data.repository.ClimateRepositoryImpl
import com.sngular.wheatherapp.domain.datasource.ClimateDataSource
import com.sngular.wheatherapp.domain.provider.ClimateUseCaseProvider
import com.sngular.wheatherapp.domain.repository.ClimateRepository
import com.sngular.wheatherapp.presentation.ClimateContract
import com.sngular.wheatherapp.presentation.ClimatePresenterImpl
import com.sngular.wheatherapp.presentation.ClimateUseCaseProviderImpl
import com.sngular.wheatherapp.presentation.view.res.ClimateStringResourcesImpl
import org.koin.dsl.module
import retrofit2.Retrofit

fun providesPromotionsEndPoint(retrofit: Retrofit): EndPoint =
    retrofit.create(EndPoint::class.java)

fun providesWeatherListResponseToWeatherListMapper(): WeatherListResponseToWeatherList =
    WeatherListResponseToWeatherList()

fun providesMainTemperatureToTemperatureMapper(): MainTemperatureToTemperature =
    MainTemperatureToTemperature()

fun providesCurrentWeatherResponseToCurrentWeatherMapper(
    weatherListResponseToWeatherList: WeatherListResponseToWeatherList,
    mainTemperatureToTemperature: MainTemperatureToTemperature
): CurrentWeatherResponseToCurrentWeather =
    CurrentWeatherResponseToCurrentWeather(
        weatherListResponseToWeatherList,
        mainTemperatureToTemperature
    )

fun providesForecastClimateToWeatherDate(
    weatherListResponseToWeatherList: WeatherListResponseToWeatherList,
    mainTemperatureToTemperature: MainTemperatureToTemperature
): ForecastClimateToWeatherDate {
    return ForecastClimateToWeatherDate(
        weatherListResponseToWeatherList,
        mainTemperatureToTemperature
    )
}

fun providesForecastResponseToForecastClimate(
    forecastClimateToWeatherDate: ForecastClimateToWeatherDate
): ForecastResponseToForecastClimate {
    return ForecastResponseToForecastClimate(forecastClimateToWeatherDate)
}

fun providesClimateDataSource(
    endPoint: EndPoint,
    currentWeatherResponseToCurrentWeather: CurrentWeatherResponseToCurrentWeather,
    forecastResponseToForecastClimate: ForecastResponseToForecastClimate
): ClimateDataSource = ClimateDataSourceImpl(
    endPoint, currentWeatherResponseToCurrentWeather, forecastResponseToForecastClimate)

fun providesClimateRepository(climateDataSource: ClimateDataSource): ClimateRepository =
    ClimateRepositoryImpl(climateDataSource)


fun providesClimateStringResources(context: Context) : ClimateContract.StringResources =
    ClimateStringResourcesImpl(context)


fun providesClimateUseCaseProvider(
    climateRepository: ClimateRepository,
    uiScheduler: UIScheduler,
    jobScheduler: JobScheduler
): ClimateUseCaseProvider = ClimateUseCaseProviderImpl(climateRepository, jobScheduler, uiScheduler)

val weatherModule = module {
    single { providesPromotionsEndPoint(get()) }
    single { providesWeatherListResponseToWeatherListMapper() }
    single { providesForecastClimateToWeatherDate(get(), get()) }
    single { providesForecastResponseToForecastClimate(get()) }
    single { providesMainTemperatureToTemperatureMapper() }
    single { providesCurrentWeatherResponseToCurrentWeatherMapper(get(), get()) }
    single { providesClimateDataSource(get(), get(), get()) }
    single { providesClimateRepository(get()) }
    single { providesClimateStringResources(get()) }
    single { providesClimateUseCaseProvider(get(), get(), get()) }
    factory<ClimateContract.Presenter> { (v: ClimateContract.BaseView) ->
        ClimatePresenterImpl(get(), v, get())
    }
}