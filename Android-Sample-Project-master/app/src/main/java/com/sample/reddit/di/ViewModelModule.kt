package com.sample.reddit.di

import androidx.lifecycle.ViewModel
import com.sample.reddit.api.RedditRepository
import com.sample.reddit.ui.main.MainViewModel
import dagger.MapKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Provider
import javax.inject.Singleton
import kotlin.reflect.KClass

@Module
class ViewModelModule {
    @Target(AnnotationTarget.FUNCTION)
    @Retention(AnnotationRetention.RUNTIME)
    @MapKey
    internal annotation class ViewModelKey(val value: KClass<out ViewModel>)

    @Provides
    @Singleton
    fun viewModelFactory(
        providerMap: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>?>
    ): DaggerViewModelFactory {
        return DaggerViewModelFactory(providerMap)
    }

    @Provides
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun mainViewModel(
        redditRepository: RedditRepository
    ): ViewModel {
        return MainViewModel(redditRepository)
    }
}