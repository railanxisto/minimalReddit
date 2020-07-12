package com.sample.reddit.ui.main

import androidx.lifecycle.ViewModel
import com.sample.reddit.api.RedditDataSource
import com.sample.reddit.api.RedditRepository
import com.sample.reddit.model.RequestParams
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.sample.reddit.model.Result
import com.sample.reddit.model.Topic
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val redditRepository: RedditRepository
) : ViewModel() {
    fun requestArticles(params: RequestParams, onSuccess: (MutableList<Topic>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val apiResponse = redditRepository.getSubreddit("androiddev")
            when (apiResponse) {
                is Result.Success -> {
                    val data = apiResponse.content.data
                    data?.children?.let {
                        println("aqui num " + it.size)
                        println("aqui id: " + it[5].topic?.id)
                    }
                }
                else -> {
                    println("AQUI ERRO")
                }
            }
        }
    }
}