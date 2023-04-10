package org.android.go.sopt

import android.app.Application
import org.android.go.sopt.util.PreferenceUtil

class Week1Application: Application() {
    override fun onCreate() {
        prefs = PreferenceUtil(applicationContext)
        super.onCreate()
    }

    companion object {
        lateinit var prefs: PreferenceUtil
    }
}