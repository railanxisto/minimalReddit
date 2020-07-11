package com.sample.reddit

import android.app.Application
import com.sample.reddit.di.AppComponent
import com.sample.reddit.di.ComponentProvider
import com.sample.reddit.di.DaggerAppComponent
import com.sample.reddit.di.MainModule
import net.danlew.android.joda.JodaTimeAndroid
import timber.log.Timber
import timber.log.Timber.DebugTree

class MainApplication : Application(), ComponentProvider {

    private lateinit var _appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        JodaTimeAndroid.init(this)
        _appComponent = DaggerAppComponent.builder().mainModule(MainModule(this)).build()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }

    override fun appComponent(): AppComponent {
        return _appComponent
    }
}
