package com.example.imagecup

import android.app.Application
import android.provider.Settings
import com.example.imagecup.utils.PrefsManager
import com.example.imagecup.utils.PrefsManager.uid
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class ImageCupApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        PrefsManager.init(applicationContext)
        Timber.plant(Timber.DebugTree())
        if(PrefsManager.uid==""){
            val uid = Settings.Secure.getString(
                applicationContext.contentResolver, Settings.Secure.ANDROID_ID)
            PrefsManager.setUid(uid)
            Timber.d("uid : $uid")
        }

    }
}