package com.example.spacex.launchFeed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.spacex.R
import kotlinx.android.synthetic.main.launch_feed_item.view.*

class LaunchFeedItemView(val parent: ViewGroup) {

    val view =
        LayoutInflater.from(parent.context).inflate(R.layout.launch_feed_item, parent, false)
    var expanded = false

    fun render(uiModel: LaunchFeedUiItem) {
        view.missionName.text = uiModel.missionName
        view.launchSiteName.text = uiModel.launchSiteName
        view.rocketName.text = uiModel.rocketName
        view.date.text = uiModel.date
        view.details.text = uiModel.details

        //The instructions are unclear whether we should be routing to a new screen here
        //"when a Launch is selected the details screen shall be presented"
        // I don't know what "The details screen" means
        (view as View).setOnClickListener { toggleExpanded() }

        Glide.with(parent).load(uiModel.url).placeholder(R.drawable.loading).into(view.image);
    }

    private fun toggleExpanded() {
        expanded = expanded.not()
        if (expanded) {
            view.details.visibility = View.VISIBLE
            view.setBackgroundColor(ContextCompat.getColor(view.context, R.color.cardview_shadow_start_color))
        } else {
            view.details.visibility = View.GONE
            view.setBackgroundColor(ContextCompat.getColor(view.context, R.color.white))
        }
    }

    class ViewHolder(val view: LaunchFeedItemView) : RecyclerView.ViewHolder(view.view) {
        fun bind(model: LaunchFeedUiItem) {
            view.render(model)
        }
    }
}
