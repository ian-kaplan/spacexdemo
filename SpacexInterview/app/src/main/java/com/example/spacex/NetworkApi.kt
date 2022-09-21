package com.example.spacex

import com.example.spacex.Launch
import io.reactivex.Single
import retrofit2.http.GET

interface NetworkApi {

    @GET("launches")
    fun fetchLaunches(): Single<List<Launch>>
}
