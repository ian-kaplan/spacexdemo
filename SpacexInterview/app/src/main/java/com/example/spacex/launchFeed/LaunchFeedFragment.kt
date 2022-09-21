package com.example.spacex.launchFeed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.spacex.R
import com.example.spacex.baseIslands.BaseIslandFragment


/**
 * With some improvements to the BaseFragment class we can avoid needing this class
 */

class LaunchFeedFragment :
    BaseIslandFragment<LaunchFeedUiEvent, LaunchFeedUiModel, LaunchFeedAction, LaunchFeedResult>() {

    override fun createBuilder(view: View): LaunchFeedBuilder {
        return LaunchFeedBuilder(
            view.context,
            view.findViewById<ViewGroup>(R.id.frame),
            LayoutInflater.from(view.context),
            LaunchFeedRepo(LaunchFeedState())
        )
    }

    override fun onStart() {
        super.onStart()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            LaunchFeedFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}