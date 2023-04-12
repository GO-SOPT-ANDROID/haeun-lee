package org.android.go.sopt.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import org.android.go.sopt.R
import org.android.go.sopt.Week2Application
import org.android.go.sopt.databinding.ActivityLoginBinding
import org.android.go.sopt.model.User
import org.android.go.sopt.ui.main.MainActivity
import org.android.go.sopt.ui.signup.SignUpActivity
import org.android.go.sopt.util.*
import org.android.go.sopt.util.extension.hideKeyboard
import org.android.go.sopt.util.extension.showSnackbar
import org.android.go.sopt.util.extension.showToast

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var userInfo: User
    private var isUserRegistered: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handleAutoLogin()
        initRootLayoutClickListener()
        initSignUpButtonClickListener()
        initLoginButtonClickListener()
    }

    private fun handleAutoLogin() {
        if (isLastUserLoggedIn()) {
            navigateToMainScreen()
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

    private fun initSignUpButtonClickListener() {
        val signUpResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                handleSignUpResult(result)
            }
        }

        binding.btnSignUp.setOnClickListener {
            signUpResultLauncher.launch(
                Intent(this, SignUpActivity::class.java)
            )
        }
    }

    private fun handleSignUpResult(result: ActivityResult) {
        isUserRegistered = true
        showSnackbar(binding.root, getString(R.string.sign_up_success_msg))

        initUserInfoFromIntent(result.data)
        saveUserInfoToPrefs()
    }

    private fun initRootLayoutClickListener() {
        binding.root.setOnClickListener {
            hideKeyboard()
        }
    }

    private fun isLastUserLoggedIn(): Boolean {
        val id = Week2Application.prefs.getString(ID_KEY, null)
        val pw = Week2Application.prefs.getString(PW_KEY, null)
        val name = Week2Application.prefs.getString(NAME_KEY, null)
        val hobby = Week2Application.prefs.getString(HOBBY_KEY, null)
        return id != null && pw != null && name != null && hobby != null
    }

    private fun navigateToMainScreen() {
        Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(this)
        }
    }

    private fun checkLoginInputValidity(): Boolean {
        val id = binding.etId.text.toString()
        val pw = binding.etPw.text.toString()
        return userInfo.id == id && userInfo.pw == pw
    }

    private fun saveUserInfoToPrefs() {
        Week2Application.prefs.setString(ID_KEY, userInfo.id)
        Week2Application.prefs.setString(PW_KEY, userInfo.pw)
        Week2Application.prefs.setString(NAME_KEY, userInfo.name)
        Week2Application.prefs.setString(HOBBY_KEY, userInfo.hobby)
    }

    private fun initUserInfoFromIntent(intent: Intent?) {
        val id = intent?.getStringExtra(ID_KEY).toString()
        val pw = intent?.getStringExtra(PW_KEY).toString()
        val name = intent?.getStringExtra(NAME_KEY).toString()
        val hobby = intent?.getStringExtra(HOBBY_KEY).toString()
        userInfo = User(id, pw, name, hobby)
    }
}

