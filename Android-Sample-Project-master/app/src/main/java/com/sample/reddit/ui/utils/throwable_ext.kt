package com.sample.reddit.ui.utils

import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun Throwable.getRestErrorMessage(): String =
    when (this) {
        is HttpException -> {
            when (code()) {
                in 400..499 -> "Some app error occurred"
                in 500..599 -> "Some server error occurred"
                else -> "An unexpected error ocurred"
            }
        }
        is UnknownHostException, is SocketTimeoutException ->
            "Network problem. Check your conectivity and try again!"
        else -> "An unexpected error ocurred"
    }