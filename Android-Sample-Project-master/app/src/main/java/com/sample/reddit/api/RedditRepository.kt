package com.sample.reddit.api

import com.sample.reddit.model.ApiResponse
import com.sample.reddit.model.CommentsResponse
import com.sample.reddit.model.RequestParams
import com.sample.reddit.model.Result
import javax.inject.Inject

interface RedditRepository {
    suspend fun getSubreddit(params: RequestParams): Result<ApiResponse>

    suspend fun getComments(id: String): Result<List<CommentsResponse>>
}

class RedditRepositoryImpl @Inject constructor(val apiService: RedditService) : RedditRepository {
    override suspend fun getSubreddit(params: RequestParams): Result<ApiResponse> {
        return try {
            Result.Success(apiService.requestSubreddit(params.limit, params.after))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getComments(id: String): Result<List<CommentsResponse>> {
        return try {
            Result.Success(apiService.requestComments(id))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
