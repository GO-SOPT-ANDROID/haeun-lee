package org.android.go.sopt.util

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.google.gson.Gson
import org.android.go.sopt.domain.model.User

class PreferenceManager(context: Context) {
    private val sharedPreferences: SharedPreferences by lazy {
        // 암호화 할 마스터 키 생성
        val masterKeyAlias = MasterKey
            .Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        // key, value를 암호화 한 sharedPreferences 객체 생성
        EncryptedSharedPreferences.create(
            context,
            FILE_NAME,
            masterKeyAlias,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    private fun putString(key: String, value: String) {
        sharedPreferences.edit { putString(key, value) }
    }

    private fun getString(key: String, defaultValue: String): String? {
        return sharedPreferences.getString(key, defaultValue)
    }

    fun putBoolean(key: String, value: Boolean){
        sharedPreferences.edit { putBoolean(key, value) }
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    fun putUserData(user: User) {
        val json = Gson().toJson(user)
        putString(Intent.EXTRA_USER, json)
    }

    fun getUserData(): User? {
        val json = getString(Intent.EXTRA_USER, "")
        return Gson().fromJson(json, User::class.java)
    }

    fun deleteAllData() {
        sharedPreferences.edit { clear() }
    }

    companion object {
        private const val FILE_NAME = "encrypted_user_info"
    }
}