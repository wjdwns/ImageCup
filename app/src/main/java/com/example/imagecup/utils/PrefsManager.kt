package com.example.imagecup.utils

import android.content.Context
import android.content.SharedPreferences

const val PREFS_UID = "UID"
object PrefsManager {
    private lateinit var prefs: SharedPreferences

    fun init(context: Context) {
        prefs = context.getSharedPreferences("ImageCup", Context.MODE_PRIVATE)
    }

    val uid: String
        get() = prefs.getString(PREFS_UID, "").toString()

    fun setUid(uid : String) {
        prefs.edit()?.apply {
            putString(PREFS_UID, uid)
        }?.apply()
    }
}