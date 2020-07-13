package com.sample.reddit.model

data class RequestParams(val limit: Int = 50, val after: String? = null)