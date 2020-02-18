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
import com.sngular.wheatherapp.domain.usecase.CurrentClimateUseCase
import com.sngular.wheatherapp.domain.usecase.ForecastClimateUseCase
import com.sngular.wheatherapp.presentation.presenter.ClimateContract
import com.sngular.wheatherapp.presentation.presenter.ClimateUseCaseProviderImpl
import com.sngular.wheatherapp.presentation.viewmodel.ClimateViewModel
import com.sngular.wheatherapp.presentation.view.res.ClimateStringResourcesImpl
import com.sngular.wheatherapp.presentation.viewmodel.ForecastViewModel
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel
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
): ClimateUseCaseProvider =
    ClimateUseCaseProviderImpl(
        climateRepository,
        jobScheduler,
        uiScheduler
    )

fun providesCurrentWeatherUseCase(climateRepository: ClimateRepository,
                                  uiScheduler: UIScheduler,
                                  jobScheduler: JobScheduler): CurrentClimateUseCase =
    CurrentClimateUseCase(climateRepository, jobScheduler, uiScheduler)

fun providesForecastClimate(climateRepository: ClimateRepository,
                            uiScheduler: UIScheduler,
                            jobScheduler: JobScheduler): ForecastClimateUseCase =
    ForecastClimateUseCase(climateRepository, jobScheduler, uiScheduler)

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
    single { providesCurrentWeatherUseCase(get(), get(), get()) }
    single { providesForecastClimate(get(), get(), get()) }
    viewModel { ClimateViewModel(get(), get()) }
    viewModel { ForecastViewModel(get(), get()) }
}