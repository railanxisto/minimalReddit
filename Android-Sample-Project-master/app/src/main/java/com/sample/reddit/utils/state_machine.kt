package com.sample.reddit.utils

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.coroutines.CoroutineContext

object StateMachine {

    operator fun <T> invoke(
        context: CoroutineContext = Dispatchers.Default,
        action: suspend () -> T
    ): Flow<StateMachineEvent<T>> =
        flow<StateMachineEvent<T>> { emit(Success(action())) }
            .catch { exception -> emit(Failure(exception)) }
            .onStart { emit(Start) }
            .onCompletion { emit(Finish) }
            .flowOn(context)
}

sealed class StateMachineEvent<out T>

object Start : StateMachineEvent<Nothing>()

data class Success<out T>(val value: T) : StateMachineEvent<T>()

data class Failure(val exception: Throwable) : StateMachineEvent<Nothing>()

object Finish : StateMachineEvent<Nothing>()