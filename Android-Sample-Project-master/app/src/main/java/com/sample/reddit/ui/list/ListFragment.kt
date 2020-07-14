package com.sample.reddit.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sample.reddit.R
import com.sample.reddit.databinding.ListFragmentBinding
import com.sample.reddit.model.Topic
import com.sample.reddit.ui.main.MainViewModel
import com.sample.reddit.ui.utils.BaseFragment
import com.sample.reddit.ui.utils.EndlessRecyclerViewScrollListener
import com.sample.reddit.ui.utils.getRestErrorMessage
import com.sample.reddit.ui.utils.isConnected

class ListFragment : BaseFragment(), ListAdapter.TopicClickListener {
    private var _binding: ListFragmentBinding? = null
    private val binding: ListFragmentBinding
        get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()
    private val adapter = ListAdapter(this)
    private val layoutManager = LinearLayoutManager(activity)
    private val endlessScroll by lazy {
        EndlessRecyclerViewScrollListener(layoutManager) {
            binding.progressBar.isVisible = true
            viewModel.requestMoreTopics()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ListFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupAdapter()
        setUpObservers()

        if (savedInstanceState == null) {
            if (requireContext().isConnected()) {
                viewModel.requestTopics()
            } else {
                showSnackbar(getString(R.string.error_no_connection))
            }
        }
    }

    private fun setUpObservers() {
        viewModel.getTopics().observe(viewLifecycleOwner, Observer {
            adapter.setTopics(it)
        })

        viewModel.getLoading().observe(viewLifecycleOwner, Observer {
            binding.swipeRefresh.isRefreshing = it
        })

        viewModel.getMoreTopics().observe(viewLifecycleOwner, Observer {
            adapter.updateTopics(it)
            binding.progressBar.isVisible = false
        })

        viewModel.getError().observe(viewLifecycleOwner, Observer {
            showError(it.getRestErrorMessage(requireContext()))
        })

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.requestTopics()
            endlessScroll.resetState()
        }
    }

    private fun setupAdapter() {
        binding.topicsRecyclerView.layoutManager = layoutManager
        binding.topicsRecyclerView.adapter = adapter
        binding.topicsRecyclerView.addOnScrollListener(endlessScroll)
    }

    private fun showError(error: String) {
        showToast(error)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onTopicClick(topic: Topic) {
        if (requireContext().isConnected()) {
            val action = ListFragmentDirections.actionListFragmentToDetailFragment(topic)
            view?.findNavController()?.navigate(action)
        } else {
            showSnackbar(getString(R.string.error_no_connection))
        }
    }
}