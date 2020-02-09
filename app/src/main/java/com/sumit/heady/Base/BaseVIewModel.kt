package com.sumit.heady.Base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewmodel : ViewModel() {
    private val disposables = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    fun getCompositeDisposable(): CompositeDisposable {
        return disposables
    }

    fun addToDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

}