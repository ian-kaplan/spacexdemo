package com.example.spacex.launchFeed

import com.example.spacex.Launch
import com.example.spacex.NetworkService
import io.reactivex.Single

class LaunchFeedRepo(val launchFeedState: LaunchFeedState) {
    private val service = NetworkService.instance

    fun fetchTrending(): Single<List<Launch>> {
        return service.fetchLaunches().map({ it })
            .doOnSuccess { launchFeedState.addItems(it) }
    }
}