package com.sngular.core.arch

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel (
    private val disposables: CompositeDisposable = CompositeDisposable()
) : ViewModel() {

    fun addDisposable(disposable: Disposable?) {
        disposable?.let {
            disposables.add(it)
        }
    }

    override fun onCleared() {
        disposables.dispose()
    }
}