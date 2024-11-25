package com.sano.ideallist

import android.app.Application
import com.sano.ideallist.di.modelModule
import com.sano.ideallist.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class PracticeApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@PracticeApp)
            modules(networkModule)
            modules(modelModule)
        }
    }
}