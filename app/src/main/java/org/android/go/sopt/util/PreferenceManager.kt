package org.android.go.sopt.util

import androidx.core.content.edit
import com.google.gson.Gson
import org.android.go.sopt.GoSoptApplication
import org.android.go.sopt.domain.model.User

class PreferenceManager {
    private val prefs = GoSoptApplication.prefs

    var loginState: Boolean
        set(value) = prefs.edit { putBoolean(KEY_LOGIN_STATE, value) }
        get() = prefs.getBoolean(KEY_LOGIN_STATE, false)

    var signedUpUser: User?
        set(value) = prefs.edit {
            val user = Gson().toJson(value)
            putString(KEY_SIGNED_UP_USER, user)
        }
        get() {
            val json = prefs.getString(KEY_SIGNED_UP_USER, "")
            return try {
                Gson().fromJson(json, User::class.java)
            } catch (e: Exception){
                null
            }
        }

    companion object {
        private const val KEY_LOGIN_STATE = "LOGIN_STATE"
        private const val KEY_SIGNED_UP_USER = "SIGNED_UP_USER"
    }
}