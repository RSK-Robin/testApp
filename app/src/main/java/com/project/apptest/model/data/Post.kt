package com.project.apptest.model.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Post(
    @SerialName("author")
    val author: String = "",
    @SerialName("canVote")
    val canVote: Boolean = false,
    @SerialName("commentsCount")
    val commentsCount: Int = 0,
    @SerialName("date")
    val date: String = "",
    @SerialName("description")
    val description: String = "",
    @SerialName("fileSize")
    val fileSize: Int = 0,
    @SerialName("gifSize")
    val gifSize: Int = 0,
    @SerialName("gifURL")
    val gifURL: String = "",
    @SerialName("height")
    val height: String = "",
    @SerialName("id")
    val id: Int = 0,
    @SerialName("previewURL")
    val previewURL: String = "",
    @SerialName("type")
    val type: String = "",
    @SerialName("videoPath")
    val videoPath: String = "",
    @SerialName("videoSize")
    val videoSize: Int = 0,
    @SerialName("videoURL")
    val videoURL: String = "",
    @SerialName("votes")
    val votes: Int = 0,
    @SerialName("width")
    val width: String = ""
)