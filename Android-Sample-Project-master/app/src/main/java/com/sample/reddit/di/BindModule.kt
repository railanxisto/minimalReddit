package com.sample.reddit.di

import com.sample.reddit.api.*
import dagger.Binds
import dagger.Module

@Module
interface BindModule {
    @Binds
    fun bindRedditRepository(redditRepository: RedditRepositoryImpl): RedditRepository
}