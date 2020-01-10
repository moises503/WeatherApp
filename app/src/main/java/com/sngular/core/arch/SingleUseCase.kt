package com.sngular.core.arch

import com.sngular.core.arch.threads.JobScheduler
import com.sngular.core.arch.threads.UIScheduler
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

abstract class SingleUseCase<Params, T>(
    private val uiScheduler: UIScheduler,
    private val jobScheduler: JobScheduler
) : UseCase<Params, Single<T>>() {

    fun execute(params: Params? = null): Single<T> {
        return buildUseCase(params)
            .subscribeOn(Schedulers.from(jobScheduler))
            .observeOn(uiScheduler.getScheduler())
    }

    fun execute(params: Params?, observer: DisposableSingleObserver<T>) {
        val observable = buildUseCase(params)
            .subscribeOn(Schedulers.from(jobScheduler))
            .observeOn(uiScheduler.getScheduler())
        subscribe(observable.subscribeWith(observer))
    }

    fun executeAndReturnDisposable(params: Params?,
                                   observer: DisposableSingleObserver<T>
    ) : Disposable? {
        val observable = buildUseCase(params)
            .subscribeOn(Schedulers.from(jobScheduler))
            .observeOn(uiScheduler.getScheduler())
        return subscribeAndReturnDisposable(observable.subscribeWith(observer))
    }
}