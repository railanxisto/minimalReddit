package com.sample.reddit.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.sample.reddit.R
import com.sample.reddit.databinding.MainActivityBinding
import com.sample.reddit.di.ComponentProvider
import com.sample.reddit.di.DaggerViewModelFactory
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: DaggerViewModelFactory

    private lateinit var binding: MainActivityBinding
    private lateinit var viewModel: MainViewModel

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        (applicationContext as ComponentProvider).appComponent().inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        navController = Navigation.findNavController(this,
            R.id.nav_controller
        )
    }
}