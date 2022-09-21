package com.example.spacex.baseIslands

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.spacex.R
import io.reactivex.disposables.CompositeDisposable

abstract class BaseIslandFragment<BaseUiEvent, BaseUiModel, BaseAction, BaseResult> : Fragment() {
    val disposables = CompositeDisposable()

    lateinit var builder: BaseIslandBuilder<BaseUiEvent, BaseUiModel, BaseAction, BaseResult>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view = inflater.inflate(R.layout.fragment_base_island, container, false)

        builder = createBuilder(view)
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    abstract fun createBuilder(view: View): BaseIslandBuilder<BaseUiEvent, BaseUiModel, BaseAction, BaseResult>

    override fun onStart() {
        super.onStart()
        builder.build()
        builder.start()
    }

    override fun onStop() {
        builder.stop()
        disposables.clear()
        super.onStop()
    }
}