package com.project.apptest

import android.app.Application
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.project.apptest.model.api.DevelopersLifeApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit.Builder

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        api = Builder()
            .baseUrl("https://developerslife.ru/")
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(
                Json
                    .asConverterFactory(MediaType.get("application/json"))
            )
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build().create(DevelopersLifeApi::class.java)
    }

    companion object {
        lateinit var instance: Application
        lateinit var api: DevelopersLifeApi
    }
}