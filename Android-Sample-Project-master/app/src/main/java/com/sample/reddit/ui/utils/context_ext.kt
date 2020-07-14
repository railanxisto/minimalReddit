package com.sample.reddit.ui.utils

import android.content.Context
import android.net.ConnectivityManager
import androidx.core.content.getSystemService

// TODO: Replace for ConnectivityManager.NetworkCallback()
fun Context.isConnected() =
    getSystemService<ConnectivityManager>()?.activeNetworkInfo?.isConnectedOrConnecting ?: false