package com.example.spacex.baseIslands

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.spacex.R
import com.example.spacex.launchFeed.LaunchFeedFragment

//This architecture is known as `Islands`, and works best with a framework running a lot of the boilerplate
class BaseIslandActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_island)

        val fragment = LaunchFeedFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .add(R.id.container, fragment).commit()

    }
}