package com.project.apptest.model.repository

import com.project.apptest.MainApplication

class PostRepository {

    private val postApi = MainApplication.api

    suspend fun getPost() = postApi.getPost()
}