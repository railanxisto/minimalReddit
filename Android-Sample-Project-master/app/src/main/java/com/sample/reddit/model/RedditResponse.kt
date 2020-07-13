package com.sample.reddit.model

import com.squareup.moshi.Json
import java.io.Serializable

data class ApiResponse(
    val kind: String?,
    val data: Data?
)

data class Data(
    val children: List<DataChildren>?,
    val after: String?,
    val before: String?
)

data class DataChildren(
    @Json(name="data") val topic: Topic?
)

data class Topic(
    val id: String,
    val title: String?,
    val thumbnail: String?,
    @Json(name="num_comments") val comments: Int = 0
) : Serializable