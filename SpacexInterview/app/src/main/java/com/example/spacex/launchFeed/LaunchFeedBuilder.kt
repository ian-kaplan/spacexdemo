package com.example.spacex.launchFeed

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.spacex.*
import com.example.spacex.baseIslands.BaseIslandBuilder
import com.example.spacex.baseIslands.BaseIslandPresenter

class LaunchFeedBuilder(
    context: Context,
    parent: ViewGroup,
    inflater: LayoutInflater,
    launchFeedRepo: LaunchFeedRepo
) : BaseIslandBuilder<LaunchFeedUiEvent, LaunchFeedUiModel, LaunchFeedAction, LaunchFeedResult>(){

    override val presenter: BaseIslandPresenter<LaunchFeedUiEvent, LaunchFeedUiModel, LaunchFeedAction, LaunchFeedResult> = LaunchFeedPresenter()
    override val view: BaseIslandView<LaunchFeedUiEvent, LaunchFeedUiModel> = LaunchFeedView(context, parent, inflater)
    override val interactor: BaseIslandInteractor<LaunchFeedAction, LaunchFeedResult> = LaunchFeedInteractor(launchFeedRepo)
}