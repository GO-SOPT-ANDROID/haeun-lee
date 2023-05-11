package org.android.go.sopt

import android.app.Application
import org.android.go.sopt.util.PreferenceManager
import timber.log.Timber

class GoSoptApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initTimber()
        initPrefsManager()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initPrefsManager() {
        prefs = PreferenceManager(applicationContext)
    }

    companion object {
        lateinit var prefs: PreferenceManager
    }
}