package com.sample.reddit.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.reddit.api.RedditRepository
import com.sample.reddit.model.CommentsResponse
import com.sample.reddit.model.DataChildren
import com.sample.reddit.model.RequestParams
import com.sample.reddit.model.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val redditRepository: RedditRepository
) : ViewModel() {

    private var after: String? = null

    private val topics = MutableLiveData<List<DataChildren>>()

    private val requestMoreTopics = MutableLiveData<List<DataChildren>>()

    private val loading = MutableLiveData<Boolean>()

    private val errorMessage = MutableLiveData<Throwable>()

    @ExperimentalCoroutinesApi
    private val _state = MutableStateFlow<Result<List<CommentsResponse>>>(
        Result.Loading
    )

    @ExperimentalCoroutinesApi
    val state: StateFlow<Result<List<CommentsResponse>>>
        get() = _state

    fun requestTopics() {
        loading.value = true
        requestTopicsFromApi(RequestParams()) {
            topics.postValue(it)
        }
    }

    fun requestMoreTopics() {
        requestTopicsFromApi(RequestParams(after = after)) {
            requestMoreTopics.postValue(it)
        }
    }

    private fun requestTopicsFromApi(
        params: RequestParams,
        onSuccess: (List<DataChildren>) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = redditRepository.getSubreddit(params)
            when (result) {
                is Result.Success -> {
                    after = result.content.data?.after ?: "end"
                    onSuccess(result.content.data?.children!!)
                    loading.postValue(false)
                }
                is Result.Error -> {
                    loading.postValue(false)
                    errorMessage.postValue(result.error)
                }
            }
        }
    }

    @ExperimentalCoroutinesApi
    fun requestComments(id: String) {
        viewModelScope.launch {
            _state.value = Result.Loading
            _state.value = withContext(Dispatchers.IO) {
                redditRepository.getComments(id)
            }
        }
    }

    fun getTopics() = topics

    fun getLoading() = loading

    fun getMoreTopics() = requestMoreTopics

    fun getError() = errorMessage
}