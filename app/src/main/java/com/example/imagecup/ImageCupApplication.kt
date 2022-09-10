package com.example.imagecup

import android.app.Application
import timber.log.Timber

class ImageCupApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        //if (BuildConfig.DEBUG) {}
        Timber.plant(Timber.DebugTree())

    }
}