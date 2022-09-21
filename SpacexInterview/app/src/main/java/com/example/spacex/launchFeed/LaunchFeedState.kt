package com.example.spacex.launchFeed

import com.example.spacex.Launch

class LaunchFeedState {
    private val items = mutableListOf<Launch>()

    fun addItems(items: List<Launch>) {
        this.items.addAll(items)
    }

    fun getNumberOfItems(): Int {
        return items.size
    }
}