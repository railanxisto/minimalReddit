package com.sample.reddit.di

import com.sample.reddit.MainActivity
import com.sample.reddit.api.ApiModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MainModule::class, ApiModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
}
