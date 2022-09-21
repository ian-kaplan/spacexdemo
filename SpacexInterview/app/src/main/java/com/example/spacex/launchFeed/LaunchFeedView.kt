package com.example.spacex.launchFeed

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.example.spacex.BaseIslandView
import com.example.spacex.R

class LaunchFeedView(
    context: Context, parent: ViewGroup, layoutInflater: LayoutInflater
) : BaseIslandView<LaunchFeedUiEvent, LaunchFeedUiModel>(context, parent, layoutInflater) {

    lateinit var adapter: LaunchFeedAdapter
    override fun layoutId(): Int {
        return R.layout.launch_feed
    }

    override fun bind() {
        super.bind()
        adapter = LaunchFeedAdapter()
    }

    override fun render(uiModel: LaunchFeedUiModel) {
        val launches = view.findViewById<RecyclerView>(R.id.launches)
        launches.adapter = adapter
        launches.layoutManager

        val loadingSpinner = view.findViewById<ProgressBar>(R.id.loadingSpinner)
        if (uiModel.isLoading) {
            loadingSpinner.visibility = View.VISIBLE
            launches.visibility = View.GONE
        } else {
            loadingSpinner.visibility = View.GONE
            launches.visibility = View.VISIBLE
        }
        adapter.setItems(uiModel.items)
        launches.scrollToPosition(uiModel.startingPosition)

        launches.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!recyclerView.canScrollVertically(1)) {
                    scrolledToBottom()
                }
            }
        })
    }

    private fun scrolledToBottom() {
        //if we want to do infinite scroll we can emit an event to the presenter here
    }
}

sealed class LaunchFeedUiEvent
data class LaunchFeedClick(val index: Int) : LaunchFeedUiEvent()

data class LaunchFeedUiItem(
    val missionName: String,
    val rocketName: String,
    val launchSiteName: String,
    val date: String,
    val url: String?,
    val details: String?,
)

data class LaunchFeedUiModel(
    val items: List<LaunchFeedUiItem> = emptyList(),
    val isLoading: Boolean = false,
    val startingPosition: Int = 0
) {
    fun withNewItems(items: List<LaunchFeedUiItem>): LaunchFeedUiModel {
        val newItems = this.items.toMutableList()
        newItems.addAll(items)
        return copy(items = newItems)
    }

    fun withLoading(isLoading: Boolean): LaunchFeedUiModel {
        return copy(isLoading = isLoading)
    }
}
