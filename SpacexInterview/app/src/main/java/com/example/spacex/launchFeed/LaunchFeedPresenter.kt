package com.example.spacex.launchFeed

import com.example.spacex.baseIslands.BaseIslandPresenter

class LaunchFeedPresenter :
    BaseIslandPresenter<LaunchFeedUiEvent, LaunchFeedUiModel, LaunchFeedAction, LaunchFeedResult>() {

    override fun onResult(result: LaunchFeedResult) {
        when (result) {
            is LaunchFeedLoadedResult -> emitUiModel(
                currentUiModel.withNewItems(result.launchItems.toUiItems().reversed())
                    .withLoading(false)
            )
            is LaunchFeedLoadingResult -> emitUiModel(currentUiModel.withLoading(true))
        }
    }

    override fun initialUiModel(): LaunchFeedUiModel {
        return LaunchFeedUiModel()
    }

    override fun onUiEvent(uiEvent: LaunchFeedUiEvent) {
        when (uiEvent) {
        }
    }

    private fun List<LaunchItem>.toUiItems(): List<LaunchFeedUiItem> {
        return map {
            LaunchFeedUiItem(
                missionName = it.missionName,
                rocketName = it.rocketName,
                launchSiteName = it.launchSiteName,
                date = it.date,
                url = it.url,
                details = it.details
            )
        }
    }
}


sealed class LaunchFeedResult
data class LaunchFeedLoadedResult(val launchItems: List<LaunchItem>) : LaunchFeedResult()
data class LaunchFeedExpandItemResult(val index: Int) : LaunchFeedResult()
object LaunchFeedLoadingResult : LaunchFeedResult()

sealed class LaunchFeedAction
