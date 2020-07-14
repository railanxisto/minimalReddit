package com.sample.reddit.model

import com.squareup.moshi.Json
import java.io.Serializable

data class CommentsResponse(
    val kind: String?,
    val data: DataComments?
)

data class DataComments(
    val children: List<DataChildrenComments>?,
    val after: String?,
    val before: String?
)

data class DataChildrenComments(
    @Json(name = "data") val comment: Comment?
)

data class Comment(
    val id: String,
    val title: String?,
    val body: String?,
    val author: String? = null,
    val score: Int = 0,
    @Json(name = "url_overridden_by_dest") val image: String? = null
) : Serializable
