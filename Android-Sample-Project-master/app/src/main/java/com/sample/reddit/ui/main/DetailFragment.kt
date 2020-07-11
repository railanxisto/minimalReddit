package com.sample.reddit.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.sample.reddit.databinding.DetailFragmentBinding

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
        // TODO - use ViewModel
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}