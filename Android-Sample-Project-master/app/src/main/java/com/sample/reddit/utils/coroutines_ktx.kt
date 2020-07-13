package com.sample.reddit.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

fun <T> Flow<T>.collectIn(
    scope: CoroutineScope,
    action: suspend (value: T) -> Unit
): Job =
    scope.launch {
        collect(action)
    }