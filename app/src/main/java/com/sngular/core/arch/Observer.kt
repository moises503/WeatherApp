package com.sngular.core.arch

import io.reactivex.observers.DisposableSingleObserver

abstract class Observer<T> : DisposableSingleObserver<T>() {
    override fun onSuccess(t: T) {}
    override fun onError(e: Throwable) {}
}