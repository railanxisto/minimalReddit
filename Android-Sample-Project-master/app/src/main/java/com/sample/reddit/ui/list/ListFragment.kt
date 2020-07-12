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
import com.sample.reddit.databinding.ListFragmentBinding
import com.sample.reddit.model.ApiResponse
import com.sample.reddit.model.RequestParams
import com.sample.reddit.model.Result
import com.sample.reddit.ui.main.MainViewModel
import com.sample.reddit.utils.*
import kotlinx.android.synthetic.main.list_fragment.*

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
        setupAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.requestArticles(RequestParams())
            .collectIn(lifecycleScope) {
                    event ->
                when (event) {
                    is Start -> showLoading(true)
                    is Success -> showSuccess(event.value)
                    is Failure -> showError(event.exception)
                    is Finish -> showLoading(false)
                }
            }
    }

    private fun setupAdapter() {
        val layoutManager = LinearLayoutManager(activity)
        topicsRecyclerView.layoutManager = layoutManager
        topicsRecyclerView.adapter = adapter
    }

    private fun showError(exception: Throwable) {
        // TODO: implement error
        println("error")
    }

    private fun showSuccess(result: Result<ApiResponse>) {
        when (result) {
            is Result.Success -> {
                adapter.updateTopics(result.content.data?.children!!)
            }
            is Result.Error -> {
                showError(result.error)
            }
        }
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
}