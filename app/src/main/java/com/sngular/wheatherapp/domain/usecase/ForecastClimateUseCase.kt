package com.sngular.wheatherapp.domain.usecase

import com.sngular.core.arch.SingleUseCase
import com.sngular.core.arch.exceptions.NullParametersException
import com.sngular.core.arch.threads.JobScheduler
import com.sngular.core.arch.threads.UIScheduler
import com.sngular.wheatherapp.domain.models.forecast.ForecastClimate
import com.sngular.wheatherapp.domain.models.Location
import com.sngular.wheatherapp.domain.repository.ClimateRepository
import io.reactivex.Single

class ForecastClimateUseCase(
    private val climateRepository: ClimateRepository,
    jobScheduler: JobScheduler, uiScheduler: UIScheduler
) : SingleUseCase<Location, ForecastClimate>(uiScheduler, jobScheduler) {

    override fun buildUseCase(params: Location?): Single<ForecastClimate> {
        return params?.let {
            climateRepository.retrieveForecastClimate(it)
        } ?: Single.error(NullParametersException())
    }

}