package com.sample.reddit.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

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

@Parcelize
data class Topic(
    val id: String,
    val title: String?,
    val thumbnail: String?,
    @Json(name="num_comments") val comments: Int = 0
) : Parcelable