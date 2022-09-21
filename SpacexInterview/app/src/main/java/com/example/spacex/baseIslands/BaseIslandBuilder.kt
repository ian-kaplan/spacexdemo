package com.example.spacex.baseIslands

import com.example.spacex.BaseIslandInteractor
import com.example.spacex.BaseIslandView
import com.example.spacex.BaseTransaction
import com.jakewharton.rxrelay2.PublishRelay

abstract class BaseIslandBuilder<BaseUiEvent, BaseUiModel, BaseAction, BaseResult>(
) {
    abstract val interactor: BaseIslandInteractor<BaseAction, BaseResult>
    abstract val presenter: BaseIslandPresenter<BaseUiEvent, BaseUiModel, BaseAction, BaseResult>
    abstract val view: BaseIslandView<BaseUiEvent, BaseUiModel>

    val islandTransactionRelay: PublishRelay<BaseTransaction> =
        PublishRelay.create<BaseTransaction>()
    val islandTransactionStream = islandTransactionRelay.hide()
    val uiModelRelay: PublishRelay<BaseUiModel> =
        PublishRelay.create<BaseUiModel>()

    fun build() {
        val resultRelay: PublishRelay<BaseResult> =
            PublishRelay.create<BaseResult>()
        val resultStream = resultRelay.hide()
        val actionRelay: PublishRelay<BaseAction> =
            PublishRelay.create<BaseAction>()
        val actionStream = actionRelay.hide()
        val uiEventRelay: PublishRelay<BaseUiEvent> by lazy { PublishRelay.create<BaseUiEvent>() }
        val uiEventStream = uiEventRelay.hide()
        val uiModelStream = uiModelRelay.hide()

        interactor.islandTransactionRelay = islandTransactionRelay
        interactor.resultsRelay = resultRelay
        interactor.actionStream = actionStream
        presenter.actionsRelay = actionRelay
        presenter.resultsStream = resultStream
        presenter.uiEventsStream = uiEventStream
        presenter.uiModelRelay = uiModelRelay
        view.uiEventRelay = uiEventRelay
        view.uiModelStream = uiModelStream
    }

    fun start() {
        interactor.bind()
        presenter.bind()
        view.bind()
        uiModelRelay.accept(presenter.initialUiModel()!!)
        interactor.start()
    }

    fun stop() {
        interactor.stop()
        presenter.stop()
        view.stop()
    }
}