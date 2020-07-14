package com.sample.reddit.di

import com.sample.reddit.api.RedditRepository
import com.sample.reddit.api.RedditRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
interface BindModule {
    @Binds
    fun bindRedditRepository(redditRepository: RedditRepositoryImpl): RedditRepository
}