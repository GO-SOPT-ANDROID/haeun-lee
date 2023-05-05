package org.android.go.sopt

import android.app.Application
import org.android.go.sopt.util.PreferenceUtil

class GoSoptApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        prefs = PreferenceUtil(applicationContext)
    }

    companion object {
        lateinit var prefs: PreferenceUtil
    }
}