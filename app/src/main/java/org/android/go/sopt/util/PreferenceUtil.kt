package org.android.go.sopt.util

import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.StringRes
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class PreferenceUtil(private val context: Context) {
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

    fun getString(@StringRes keyId: Int, defaultValue: String?): String? {
        val key = context.getString(keyId)
        return sharedPreferences.getString(key, defaultValue)
    }

    fun putString(@StringRes keyId: Int, newValue: String) {
        val key = context.getString(keyId)
        sharedPreferences.edit().putString(key, newValue).apply()
    }

    fun deleteAllData() {
        sharedPreferences.edit().clear().apply()
    }

    companion object {
        private const val FILE_NAME = "encrypted_user_info"
    }
}