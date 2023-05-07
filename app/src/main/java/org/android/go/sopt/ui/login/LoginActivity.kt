package org.android.go.sopt.ui.login

import android.content.Intent
import android.content.Intent.EXTRA_USER
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import org.android.go.sopt.R
import org.android.go.sopt.GoSoptApplication
import org.android.go.sopt.binding.BindingActivity
import org.android.go.sopt.databinding.ActivityLoginBinding
import org.android.go.sopt.model.User
import org.android.go.sopt.ui.main.MainActivity
import org.android.go.sopt.ui.signup.SignUpActivity
import org.android.go.sopt.util.extension.getCompatibleSerializableExtra
import org.android.go.sopt.util.extension.hideKeyboard
import org.android.go.sopt.util.extension.showSnackbar
import org.android.go.sopt.util.extension.showToast

class LoginActivity : BindingActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private lateinit var userInfo: User
    private var isUserRegistered: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        handleAutoLogin()
        initRootLayoutClickListener()
        initLoginButtonClickListener()
        initSignUpButtonClickListener()
    }

    private fun handleAutoLogin() {
        if (isLastUserLoggedIn()) {
            navigateToMainScreen()
        }
    }

    private fun isLastUserLoggedIn(): Boolean {
        val savedUserInfo = GoSoptApplication.prefs.getUserData()
        if (savedUserInfo != null) {
            return checkSavedUserInfo(savedUserInfo)
        }
        return false
    }

    private fun checkSavedUserInfo(savedUserInfo: User): Boolean {
        return savedUserInfo.id.isNotBlank() &&
                savedUserInfo.pw.isNotBlank() &&
                savedUserInfo.name.isNotBlank() &&
                savedUserInfo.hobby.isNotBlank()
    }

    private fun navigateToMainScreen() {
        Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(this)
        }
    }

    private fun initLoginButtonClickListener() {
        binding.btnLogin.setOnClickListener {
            if (isUserRegistered) {
                handleLoginResult()
            } else {
                showToast(getString(R.string.unregistered_msg))
            }
        }
    }

    private fun handleLoginResult() {
        if (checkLoginInputValidity()) {
            showToast(getString(R.string.login_success_msg))
            navigateToMainScreen()
        } else {
            showToast(getString(R.string.login_fail_msg))
        }
    }

    private fun checkLoginInputValidity(): Boolean {
        val id = binding.etId.text.toString()
        val pw = binding.etPw.text.toString()
        return userInfo.id == id && userInfo.pw == pw
    }

    private fun initSignUpButtonClickListener() {
        val signUpResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                handleSignUpResult(result)
            }
        }

        binding.btnSignUp.setOnClickListener {
            initInputState(it)
            signUpResultLauncher.launch(
                Intent(this, SignUpActivity::class.java)
            )
        }
    }

    private fun initInputState(button: View?) {
        hideKeyboard()
        clearEditText()
        focusOutEditText(button)
    }

    private fun clearEditText() {
        binding.etId.text.clear()
        binding.etPw.text.clear()
    }

    private fun focusOutEditText(button: View?) {
        binding.etId.clearFocus()
        binding.etPw.clearFocus()
        button?.requestFocus()
    }

    private fun handleSignUpResult(result: ActivityResult) {
        isUserRegistered = true
        showSnackbar(binding.root, getString(R.string.sign_up_success_msg))
        initUserInfoFromIntent(result.data)
        saveUserInfoToPrefs()
    }

    private fun initUserInfoFromIntent(intent: Intent?) {
        intent?.getCompatibleSerializableExtra<User>(EXTRA_USER)?.apply {
            userInfo = this
        }
    }

    private fun saveUserInfoToPrefs() {
        GoSoptApplication.prefs.putUserData(userInfo)
    }

    private fun initRootLayoutClickListener() {
        binding.root.setOnClickListener {
            hideKeyboard()
        }
    }
}

