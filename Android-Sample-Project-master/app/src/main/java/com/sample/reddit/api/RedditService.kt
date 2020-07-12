package com.sample.reddit.api

import com.sample.reddit.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RedditService {
    @GET("r/androiddev.json")
    suspend fun requestSubreddit(
        @Query("limit") limit: Int = 50,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null
    ): ApiResponse
}