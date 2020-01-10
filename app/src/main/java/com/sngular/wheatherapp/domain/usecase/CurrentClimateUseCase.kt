package com.sngular.wheatherapp.domain.usecase

import com.sngular.core.arch.SingleUseCase
import com.sngular.core.arch.exceptions.NullParametersException
import com.sngular.core.arch.threads.JobScheduler
import com.sngular.core.arch.threads.UIScheduler
import com.sngular.wheatherapp.domain.models.current.CurrentClimate
import com.sngular.wheatherapp.domain.models.Location
import com.sngular.wheatherapp.domain.repository.ClimateRepository
import io.reactivex.Single

class CurrentClimateUseCase(
    private val climateRepository: ClimateRepository,
    jobScheduler: JobScheduler, uiScheduler: UIScheduler
) : SingleUseCase<Location, CurrentClimate>(uiScheduler, jobScheduler) {

    override fun buildUseCase(params: Location?): Single<CurrentClimate> {
        return params?.let {
            climateRepository.retrieveCurrentClimate(it)
        } ?: Single.error(NullParametersException())
    }
}