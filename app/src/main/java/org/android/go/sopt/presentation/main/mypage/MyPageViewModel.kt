package org.android.go.sopt.presentation.main.mypage

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.android.go.sopt.data.entity.User
import org.android.go.sopt.util.PreferenceManager

class MyPageViewModel : ViewModel() {
    private val preferenceManager = PreferenceManager()
    private val _signedUpUser = MutableLiveData<User>()
    val signedUpUser: User?
        get() = _signedUpUser.value

    init {
        getSignedUpUser()
    }

    private fun getSignedUpUser() {
        _signedUpUser.value = preferenceManager.signedUpUser
    }

    fun logout() {
        preferenceManager.loginState = false
    }
}