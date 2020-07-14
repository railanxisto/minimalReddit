package com.sample.reddit.api

import com.sample.reddit.model.ApiResponse
import com.sample.reddit.model.CommentsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RedditService {
    @GET("r/mAndroidDev.json")
    suspend fun requestSubreddit(
        @Query("limit") limit: Int = 50,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null
    ): ApiResponse

    @GET("comments/{id}/.json")
    suspend fun requestComments(
        @Path("id") id: String
    ): List<CommentsResponse>
}