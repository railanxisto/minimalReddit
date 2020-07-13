package com.sample.reddit.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sample.reddit.databinding.ListFragmentBinding
import com.sample.reddit.model.ApiResponse
import com.sample.reddit.model.Result
import com.sample.reddit.ui.main.MainViewModel
import kotlinx.android.synthetic.main.list_fragment.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ListFragment : Fragment(), ListAdapter.TopicClickListener {
    private var _binding: ListFragmentBinding? = null
    private val binding: ListFragmentBinding
        get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()
    private val adapter = ListAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ListFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.requestArticles()
        setupAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    is Result.Loading -> showLoading(true)
                    is Result.Success -> showSuccess(state.content)
                    is Result.Error -> showError(state.error)
                }
            }
        }
    }

    private fun setupAdapter() {
        val layoutManager = LinearLayoutManager(activity)
        topicsRecyclerView.layoutManager = layoutManager
        topicsRecyclerView.adapter = adapter

        topicsRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (isBottom(dy, layoutManager)) {
                    viewModel.requestMoreArticles()
                }
            }
        })
    }

    private fun showError(exception: Throwable) {
        showLoading(false)
        // TODO: implement error
        println("error")
    }

    private fun showSuccess(result: ApiResponse) {
        showLoading(false)
        adapter.updateTopics(result.data?.children!!)
    }

    private fun showLoading(show: Boolean) {
        binding.progressBar.isVisible = show
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onTopicClick() {
        TODO("Not yet implemented")
    }

    fun isBottom(dy: Int, layoutManager: LinearLayoutManager): Boolean {
        var isEndless = false
        if (dy > 0) {
            if (layoutManager.childCount + layoutManager.findFirstVisibleItemPosition() >= layoutManager.itemCount) {
                isEndless = true
            }
        }
        return isEndless
    }
}