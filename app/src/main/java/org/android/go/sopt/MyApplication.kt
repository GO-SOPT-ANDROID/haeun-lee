package org.android.go.sopt

import android.app.Application

class MyApplication: Application() {
    override fun onCreate() {
        prefs = PreferenceUtil(applicationContext)
        super.onCreate()
    }

    companion object {
        lateinit var prefs: PreferenceUtil
    }
}