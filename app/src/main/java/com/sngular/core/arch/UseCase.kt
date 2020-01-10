package com.sngular.core.arch

import io.reactivex.disposables.Disposable

abstract class UseCase<Params, Observable : Any> {

    private var disposable: Disposable? = null

    protected abstract fun buildUseCase(params: Params? = null): Observable

    fun dispose() {
        disposable?.let {
            if (!it.isDisposed) {
                it.dispose()
            }
        }
    }

    protected fun subscribe(subscription: Disposable? = null) {
        disposable = subscription
    }

    protected fun subscribeAndReturnDisposable(subscription: Disposable? = null) : Disposable? {
        disposable = subscription
        return  disposable
    }

}