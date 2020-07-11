package com.sample.reddit.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.sample.reddit.MainApplication
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MainModule(private val application: MainApplication) {
    @Provides
    @Singleton
    fun providesMoshi(): Moshi {
        return Moshi.Builder().build()
    }

    @Provides
    @Singleton
    fun providesApplication(): Application = application

    @Provides
    @Singleton
    fun providesApplicationContext(): Context = application

    @Provides
    @Singleton
    fun providesSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(
            "com.sample.reddit.MainPreferences",
            Context.MODE_PRIVATE
        )
    }
}
