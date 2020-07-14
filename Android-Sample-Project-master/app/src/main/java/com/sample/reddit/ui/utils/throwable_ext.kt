package com.sample.reddit.ui.utils

import android.content.Context
import com.sample.reddit.R
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun Throwable.getRestErrorMessage(context: Context): String =
    when (this) {
        // TODO: Improve error messages
        is HttpException -> {
            when (code()) {
                in 400..499 -> context.getString(R.string.error_app)
                in 500..599 -> context.getString(R.string.error_server)
                else -> context.getString(R.string.error_unknown)
            }
        }
        is UnknownHostException, is SocketTimeoutException ->
            context.getString(R.string.error_network)
        else -> context.getString(R.string.error_unknown)
    }