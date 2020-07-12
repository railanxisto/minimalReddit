package com.sample.reddit.api

import com.sample.reddit.model.ApiResponse
import com.sample.reddit.model.RequestParams
import com.sample.reddit.model.Result
import javax.inject.Inject

interface RedditRepository {
    suspend fun getSubreddit(subreddit: String): Result<ApiResponse>
}

class RedditRepositoryImpl @Inject constructor(val apiService: RedditService) : RedditRepository {
    override suspend fun getSubreddit(subreddit: String): Result<ApiResponse> {
        val params = RequestParams(limit = 50, after = null)
        return try {
            Result.Success(apiService.requestSubreddit(params.limit,  params.after))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
