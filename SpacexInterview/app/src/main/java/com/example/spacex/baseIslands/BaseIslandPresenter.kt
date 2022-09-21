package com.example.spacex.baseIslands

import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

abstract class BaseIslandPresenter<BaseUiEvent, BaseUiModel, BaseAction, BaseResult> {

    //To handle screen rotations the framework should put the ui model inside a view model that's
    // bound to the fragment lifecycle
    var currentUiModel: BaseUiModel = initialUiModel()
    val disposables = CompositeDisposable()

    init {
    }

    lateinit var actionsRelay: PublishRelay<BaseAction>
    lateinit var uiEventsStream: Observable<BaseUiEvent>
    lateinit var resultsStream: Observable<BaseResult>
    lateinit var uiModelRelay: PublishRelay<BaseUiModel>

    abstract fun initialUiModel(): BaseUiModel

    abstract fun onResult(result: BaseResult)

    abstract fun onUiEvent(uiEvent: BaseUiEvent)

    fun emitUiModel(uiModel: BaseUiModel) {
        currentUiModel = uiModel
        uiModelRelay.accept(currentUiModel)
    }

    fun emitAction(action: BaseAction) {
        actionsRelay.accept(action)
    }

    fun bind() {
        disposables.add(resultsStream.subscribe {
            onResult(it)
        })
        disposables.add(uiEventsStream.subscribe {
            onUiEvent(it)
        })
    }

    fun stop() {
        disposables.dispose()
    }

}
