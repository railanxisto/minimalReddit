package com.sample.reddit.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.sample.reddit.databinding.DetailFragmentBinding
import com.sample.reddit.model.CommentsResponse
import com.sample.reddit.model.Result
import com.sample.reddit.model.Topic
import com.sample.reddit.ui.main.MainViewModel
import com.sample.reddit.utils.*
import kotlinx.android.synthetic.main.list_fragment.*

class DetailFragment : Fragment() {
    private var _binding: DetailFragmentBinding? = null
    private val binding: DetailFragmentBinding
        get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val topic = arguments?.getParcelable<Topic>("topic")!!
        viewModel.requestComments(topic.id)
            .collectIn(lifecycleScope) { event ->
                when (event) {
                    is Start -> showLoading(true)
                    is Success -> showSuccess(event.value)
                    is Failure -> showError(event.exception)
                    is Finish -> showLoading(false)
                }
            }
    }

    private fun showError(exception: Throwable) {
        // TODO: implement error
        println("error")
    }

    private fun showSuccess(result: Result<List<CommentsResponse>>) {
        when (result) {
            is Result.Success -> {
                println("aqui all " + result.content)
                val topic = result.content.get(0).data?.children
                val list = result.content.get(1).data?.children
                println("aqui main" + topic)
                println("aqui comments " + list)
            }
            is Result.Error -> {
                println(result.error)
                showError(result.error)
            }
        }
    }

    private fun showLoading(show: Boolean) {
        binding.progressBar.isVisible = show
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}