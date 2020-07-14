package com.sample.reddit.di

import com.sample.reddit.ui.main.MainActivity
import com.sample.reddit.api.ApiModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MainModule::class, ApiModule::class, ViewModelModule::class, BindModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
}
