package com.sample.reddit.ui.main

import androidx.lifecycle.ViewModel
import com.sample.reddit.api.RedditRepository
import com.sample.reddit.model.ApiResponse
import com.sample.reddit.model.RequestParams
import kotlinx.coroutines.Dispatchers
import com.sample.reddit.model.Result
import com.sample.reddit.utils.StateMachine
import com.sample.reddit.utils.StateMachineEvent
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val redditRepository: RedditRepository
) : ViewModel() {

    fun requestArticles(params: RequestParams): Flow<StateMachineEvent<Result<ApiResponse>>> =
        StateMachine(Dispatchers.IO) {
            redditRepository.getSubreddit(params)
        }
}