package com.example.spacex.launchFeed

import com.example.spacex.BaseIslandInteractor
import com.example.spacex.Launch

class LaunchFeedInteractor(private val repo: LaunchFeedRepo) :
    BaseIslandInteractor<LaunchFeedAction, LaunchFeedResult>() {

    override fun start() {
        disposables.add(repo.fetchTrending().doOnSubscribe {
            emitResult(LaunchFeedLoadingResult)
        }.subscribe({
            emitResult(LaunchFeedLoadedResult(launchItems = it.toLaunchItems()))
        }, {
        }))
    }

    fun pause() {
        disposables.clear()
    }

    override fun onAction(action: LaunchFeedAction) {
    }

    private fun loadMoreImages() {
        disposables.add(repo.fetchTrending().doOnSubscribe {
            emitResult(LaunchFeedLoadingResult)
        }.subscribe({
            emitResult(LaunchFeedLoadedResult(it.toLaunchItems()))
        }, {
        }))
    }

    fun List<Launch>.toLaunchItems(): List<LaunchItem> {
        return map {
            it.toLaunchItem()
        }
    }

    fun Launch.toLaunchItem(): LaunchItem {
        return LaunchItem(
            missionName = mission_name,
            launchSiteName = launch_site.site_name,
            rocketName = rocket.rocket_name,
            date = launch_date_local,
            url = links.mission_patch,
            details = details
        )
    }
}

data class LaunchItem(
    val missionName: String,
    val launchSiteName: String,
    val rocketName: String,
    val date: String,
    val url: String?,
    val details: String?
)
