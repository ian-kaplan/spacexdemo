package com.example.spacex.launchFeed

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class LaunchFeedAdapter() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val uiItems = mutableListOf<LaunchFeedUiItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return LaunchFeedItemView.ViewHolder(LaunchFeedItemView(parent))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as LaunchFeedItemView.ViewHolder).apply {
            bind(uiItems[position])
        }
    }

    fun setItems(items: List<LaunchFeedUiItem>) {
        uiItems.clear()
        uiItems.addAll(items)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return uiItems.size
    }
}
