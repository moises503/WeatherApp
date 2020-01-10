package com.sngular.core.arch

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BasePresenter(
    private val disposables: CompositeDisposable = CompositeDisposable()
) {

    fun addDisposable(disposable: Disposable?) {
        disposable?.let {
            disposables.add(it)
        }
    }

    fun destroy() {
        disposables.dispose()
    }

}