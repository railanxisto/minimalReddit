package com.sample.reddit.model

sealed class Result<out R> {

    data class Success<out T>(val content: T) : Result<T>()

    data class Error(val error: Exception) : Result<Nothing>()

    data class Loading(val loading: Boolean): Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success data: $content"
            is Error -> "Error: $error"
            is Loading -> "Loading"
        }
    }
}