package com.sngular.wheatherapp.presentation

import com.sngular.core.arch.threads.JobScheduler
import com.sngular.core.arch.threads.UIScheduler
import com.sngular.wheatherapp.domain.provider.ClimateUseCaseProvider
import com.sngular.wheatherapp.domain.repository.ClimateRepository
import com.sngular.wheatherapp.domain.usecase.CurrentClimateUseCase
import com.sngular.wheatherapp.domain.usecase.ForecastClimateUseCase

class ClimateUseCaseProviderImpl(
    private val climateRepository: ClimateRepository,
    private val jobScheduler: JobScheduler,
    private val uiScheduler: UIScheduler
) : ClimateUseCaseProvider {

    override fun providesCurrentWeatherUseCase(): CurrentClimateUseCase {
        return CurrentClimateUseCase(climateRepository, jobScheduler, uiScheduler)
    }

    override fun providesForecastClimate(): ForecastClimateUseCase {
        return ForecastClimateUseCase(climateRepository, jobScheduler, uiScheduler)
    }
}