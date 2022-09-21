package com.example.spacex

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory


class NetworkService {

    private var retrofit: Retrofit = Builder()
        .baseUrl("https://api.spacexdata.com/v3/")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private var api: NetworkApi = retrofit.create(NetworkApi::class.java)


    fun fetchLaunches(): Single<List<Launch>> {
        return api.fetchLaunches().subscribeOn(Schedulers.newThread()).observeOn(
            AndroidSchedulers.mainThread()
        )
    }

    companion object {
        val instance = NetworkService()
    }
}

data class Pagination(
    val offset: Int,
    val total_count: Int,
    val count: Int,
)


data class Launch(
    val flight_number: Int,
    val mission_name: String,
    val rocket: Rocket,
    val launch_site: LaunchSite,
    val launch_date_local: String,
    val links: Links,
    val details: String?
)

data class Rocket(
    val rocket_name: String,
)

data class Links(
    val mission_patch: String,
)

data class LaunchSite(
    val site_name: String,
)
