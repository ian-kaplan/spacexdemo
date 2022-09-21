package com.example.spacex

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

abstract class BaseIslandView<BaseUiEvent, BaseUiModel>(
    val context: Context,
    val parent: ViewGroup,
    val layoutInflater: LayoutInflater,
) {
    lateinit var view: View
    val disposables = CompositeDisposable()

    lateinit var uiEventRelay: PublishRelay<BaseUiEvent>
    lateinit var uiModelStream: Observable<BaseUiModel>


    private fun inflate() {
        view = layoutInflater.inflate(layoutId(), parent)
    }

    open fun bind() {
        inflate()
        disposables.add(uiModelStream.subscribe {
            render(it)
        })
    }

    fun stop() {
        disposables.dispose()
    }

    abstract fun layoutId(): Int

    abstract fun render(uiModel: BaseUiModel)
}