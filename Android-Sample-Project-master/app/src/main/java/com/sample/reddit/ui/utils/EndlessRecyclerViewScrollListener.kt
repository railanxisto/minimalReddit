package com.sample.reddit.ui.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class EndlessRecyclerViewScrollListener(
    private val layoutManager: LinearLayoutManager,
    private val onReached: (Int) -> Unit
) : RecyclerView.OnScrollListener() {

    private val visibleThreshold = 20
    private val startingPageIndex = 0

    private var currentPage = 0
    private var previousTotalItemCount = 0
    private var loading = true

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

        val totalItemCount = layoutManager.itemCount

        if (totalItemCount < previousTotalItemCount) {
            currentPage = startingPageIndex
            previousTotalItemCount = totalItemCount
            if (totalItemCount == 0) loading = true
        }

        if (loading && totalItemCount > previousTotalItemCount) {
            loading = false
            previousTotalItemCount = totalItemCount
        }

        if (!loading && lastVisibleItemPosition + visibleThreshold > totalItemCount) {
            currentPage++
            loading = true
            onReached.invoke(currentPage)
        }
    }

    fun resetState() {
        currentPage = this.startingPageIndex
        previousTotalItemCount = 0
        loading = true
    }
}
