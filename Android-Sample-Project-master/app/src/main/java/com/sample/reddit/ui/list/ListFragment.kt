package com.sample.reddit.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sample.reddit.databinding.ListFragmentBinding
import com.sample.reddit.model.*
import com.sample.reddit.ui.main.MainViewModel
import com.sample.reddit.ui.utils.BaseFragment
import com.sample.reddit.ui.utils.isConnected

class ListFragment : BaseFragment(), ListAdapter.TopicClickListener {
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
        setUpObservers()

        if (savedInstanceState == null){
            if (requireContext().isConnected()) {
                viewModel.requestTopics()
            } else {
                showSnackbar("No Connection")
            }
        }
    }


    private fun setUpObservers() {
        viewModel.getTopics().observe(viewLifecycleOwner, Observer {
            adapter.setTopics(it)
        })

        viewModel.getLoading().observe(viewLifecycleOwner, Observer {
            showLoading(it)
        })

        viewModel.getMoreTopics().observe(viewLifecycleOwner, Observer {
            adapter.updateTopics(it)
        })

        viewModel.getError().observe(viewLifecycleOwner, Observer {
            showError(it)
        })

    }

    private fun setupAdapter() {
        val layoutManager = LinearLayoutManager(activity)
        binding.topicsRecyclerView.layoutManager = layoutManager
        binding.topicsRecyclerView.adapter = adapter
        binding.topicsRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (isBottom(dy, layoutManager)) {
                    viewModel.requestMoreTopics()
                }
            }
        })
    }

    private fun showError(error: String) {
        showToast(error)
    }

    private fun showLoading(show: Boolean) {
        binding.progressBar.isVisible = show
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onTopicClick(topic: Topic) {
        val action = ListFragmentDirections.actionListFragmentToDetailFragment(topic)
        view?.findNavController()?.navigate(action)
    }

    fun isBottom(dy: Int, layoutManager: LinearLayoutManager): Boolean {
        if (dy > 0) {
            if (layoutManager.childCount + layoutManager.findFirstVisibleItemPosition() >= layoutManager.itemCount) {
                return true
            }
        }
        return false
    }
}