package com.sample.reddit.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.reddit.api.RedditRepository
import com.sample.reddit.model.ApiResponse
import com.sample.reddit.model.RequestParams
import kotlinx.coroutines.Dispatchers
import com.sample.reddit.model.Result
import com.sample.reddit.utils.StateMachine
import com.sample.reddit.utils.StateMachineEvent
import com.sample.reddit.utils.Success
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.switchMap
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val redditRepository: RedditRepository
) : ViewModel() {
    var after: String? = null
    @ExperimentalCoroutinesApi
    private val _state = MutableStateFlow<Result<ApiResponse>>(
        Result.Loading(true)
    )

    @ExperimentalCoroutinesApi
    val state: StateFlow<Result<ApiResponse>> get() = _state

    fun requestMoreArticles() =
        requestArticles(RequestParams(after = after))

    @ExperimentalCoroutinesApi
    fun requestArticles(params: RequestParams = RequestParams()) {
        viewModelScope.launch {
            _state.value = Result.Loading(true)
            _state.value = withContext(Dispatchers.IO) {
                redditRepository.getSubreddit(params)
            }
        }

    }

}