package com.sample.reddit.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

class DaggerViewModelFactory @Inject constructor(
    private val providerMap: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>?>
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return providerMap[modelClass]!!.get() as T
    }
}
