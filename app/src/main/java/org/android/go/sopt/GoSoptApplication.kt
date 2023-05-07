package org.android.go.sopt

import android.app.Application
import org.android.go.sopt.util.PreferenceManager

class GoSoptApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        prefs = PreferenceManager(applicationContext)
    }

    companion object {
        lateinit var prefs: PreferenceManager
    }
}