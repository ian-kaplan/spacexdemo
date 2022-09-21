package com.example.spacex

import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable


abstract class BaseIslandInteractor<BaseAction, BaseResult> {
    val disposables = CompositeDisposable()

    lateinit var islandTransactionRelay: PublishRelay<BaseTransaction>
    lateinit var resultsRelay: PublishRelay<BaseResult>
    lateinit var actionStream: Observable<BaseAction>

    fun bind() {
        disposables.add(actionStream.subscribe {
            onAction(it)
        })
    }

    abstract fun start()

    fun stop() {
        disposables.dispose()
    }

    fun emitResult(result: BaseResult) {
        resultsRelay.accept(result)
    }

    abstract fun onAction(action: BaseAction)
}


open class BaseTransaction
