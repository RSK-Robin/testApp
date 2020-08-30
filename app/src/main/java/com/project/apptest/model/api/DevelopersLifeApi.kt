package com.project.apptest.model.api

import com.project.apptest.model.data.Post
import retrofit2.http.GET

@Suppress("ComplexInterface")
interface DevelopersLifeApi {
    @GET("random?json=true")
    suspend fun getPost(): Post
}