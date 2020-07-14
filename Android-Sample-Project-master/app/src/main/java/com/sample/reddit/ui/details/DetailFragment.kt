package com.sample.reddit.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import coil.api.load
import com.sample.reddit.databinding.DetailFragmentBinding
import com.sample.reddit.model.CommentsResponse
import com.sample.reddit.model.DataChildrenComments
import com.sample.reddit.model.Result
import com.sample.reddit.model.Topic
import com.sample.reddit.ui.main.MainViewModel
import com.sample.reddit.utils.*
import kotlinx.android.synthetic.main.detail_fragment.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class DetailFragment : Fragment() {
    private var _binding: DetailFragmentBinding? = null
    private val binding: DetailFragmentBinding
        get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()
    private val adapter = CommentsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupAdapter()

        val topic = arguments?.getParcelable<Topic>("topic")!!
        if (savedInstanceState == null)
            viewModel.requestComments(topic.id)
        lifecycleScope.launch {
            viewModel.state.collect() { state ->
                when (state) {
                    is Result.Loading -> showLoading(true)
                    is Result.Success -> showSuccess(state)
                    is Result.Error -> showError(state.error)
                }
            }
        }
    }

    private fun setupAdapter() {
        val layoutManager = LinearLayoutManager(activity)
        binding.commentsRecyclerView.layoutManager = layoutManager
        binding.commentsRecyclerView.adapter = adapter
        val dividerItemDecoration = DividerItemDecoration(
            binding.commentsRecyclerView.context,
            layoutManager.orientation
        )
        binding.commentsRecyclerView.addItemDecoration(dividerItemDecoration)
    }

    private fun showError(exception: Throwable) {
        // TODO: implement error
        println("error")
    }

    private fun showSuccess(result: Result<List<CommentsResponse>>) {
        when (result) {
            is Result.Success -> {
                val topic = result.content.first().data?.children?.first()
                val list = result.content[1].data?.children
                showTopic(topic)
                list?.let {
                    adapter.setComments(it)
                }
                showLoading(false)
            }
            is Result.Error -> {
                showError(result.error)
            }
        }
    }

    private fun showTopic(topic: DataChildrenComments?) {
        topic?.comment?.image?.let {
            binding.topicImageView.isVisible = true
            binding.topicImageView.load(it)
        } ?: run {
            binding.topicImageView.isVisible = false
        }
        // TODO: Use resources.string
        authorTextView.text = "Posted by ${topic?.comment?.author}"
        scoreTextView.text = topic?.comment?.score.toString()
        titleTextView.text = topic?.comment?.title
    }

    private fun showLoading(show: Boolean) {
        binding.progressBar.isVisible = show
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}