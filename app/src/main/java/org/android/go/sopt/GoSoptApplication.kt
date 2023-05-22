package org.android.go.sopt

import android.app.Application
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import timber.log.Timber

class GoSoptApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initTimber()
        initSharedPreferences()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initSharedPreferences() {
        prefs = getSharedPreferences()
    }

    private fun getSharedPreferences(): SharedPreferences = EncryptedSharedPreferences.create(
        applicationContext,
        applicationContext.packageName,
        MasterKey.Builder(applicationContext).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build(),
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
    )

    companion object {
        lateinit var prefs: SharedPreferences
    }
}