package com.sample.reddit.ui.main

import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sample.reddit.api.RedditRepository
import com.sample.reddit.model.*
import kotlinx.coroutines.Dispatchers
import com.sample.reddit.utils.StateMachine
import com.sample.reddit.utils.StateMachineEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val redditRepository: RedditRepository
) : ViewModel() {

    private var after: String? = null

    private val topics = MutableLiveData<List<DataChildren>>()

    private val requestMoreTopics = MutableLiveData<List<DataChildren>>()

    private val loading = MutableLiveData<Boolean>()

    private val error = MutableLiveData<Exception>()

    fun requestTopics() {
        loading.value = true
        requestTopic(RequestParams()) {
            topics.postValue(it)
        }
    }

    fun requestMoreTopics() {
        requestTopic(RequestParams(after = after)) {
            requestMoreTopics.postValue(it)
        }
    }

    fun requestTopic(params: RequestParams, onSuccess: (List<DataChildren>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = redditRepository.getSubreddit(params)
            when (result) {
                is Result.Success -> {
                    after = result.content.data?.after ?: after
                    onSuccess(result.content.data?.children!!)
                    val mainThread = Looper.myLooper() == Looper.getMainLooper()
                    loading.postValue(false)
                }
                is Result.Error -> {
                    error.value = result.error
                }
            }
        }
    }

    fun requestComments(id: String): Flow<StateMachineEvent<Result<List<CommentsResponse>>>> =
        StateMachine(Dispatchers.IO) {
            redditRepository.getComments(id)
        }

    fun getTopics() = topics

    fun getLoading() = loading

    fun getMoreTopics() = requestMoreTopics

    fun getError() = error

}