package org.android.go.sopt.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import org.android.go.sopt.GoSoptApplication
import org.android.go.sopt.R
import org.android.go.sopt.data.remote.AuthFactory
import org.android.go.sopt.data.remote.model.ReqLoginDto
import org.android.go.sopt.data.remote.model.ResLoginDto
import org.android.go.sopt.databinding.ActivityLoginBinding
import org.android.go.sopt.domain.model.User
import org.android.go.sopt.ui.main.MainActivity
import org.android.go.sopt.ui.signup.SignUpActivity
import org.android.go.sopt.util.binding.BindingActivity
import org.android.go.sopt.util.extension.hideKeyboard
import org.android.go.sopt.util.extension.showSnackbar
import retrofit2.Call
import retrofit2.Response
import timber.log.Timber

class LoginActivity : BindingActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private var savedUser: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        handleAutoLogin()
        initRootLayoutClickListener()
        initSignUpButtonClickListener()
        initLoginButtonClickListener()
    }

    private fun initLoginButtonClickListener() {
        binding.btnLogin.setOnClickListener {
            if (checkInputValidity()) {
                checkSavedUser()
                return@setOnClickListener
            }

            showSnackbar(binding.root, getString(R.string.invalid_input_error))
        }
    }

    private fun checkSavedUser() {
        // pref에 등록된 유저 정보가 아예 없는 경우 (회원가입 안 한 경우)
        savedUser = GoSoptApplication.prefs.getUserData()
        if (savedUser == null) {
            showSnackbar(binding.root, getString(R.string.unregistered_error))
            return
        }

        // pref에 등록된 id, pw와 입력값 비교
        if (compareInputWithPrefs()) {
            loginToServer()
            saveLoginStatusToPrefs()
            navigateToMainScreen()
        } else {
            showSnackbar(binding.root, getString(R.string.login_fail_msg))
        }
    }

    private fun saveLoginStatusToPrefs() {
        GoSoptApplication.prefs.putBoolean(LOGIN_STATUS_KEY, true)
    }

    private fun compareInputWithPrefs(): Boolean {
        val id = binding.etId.text.toString()
        val pw = binding.etPw.text.toString()
        return savedUser?.id == id && savedUser?.pw == pw
    }

    private fun loginToServer() {
        val id = binding.etId.text.toString()
        val pw = binding.etPw.text.toString()
        AuthFactory.ServicePool.authService.login(ReqLoginDto(id, pw))
            .enqueue(object : retrofit2.Callback<ResLoginDto> {
                override fun onResponse(
                    call: Call<ResLoginDto>,
                    response: Response<ResLoginDto>
                ) {
                    handleLoginRetrofitResponse(response)
                }

                override fun onFailure(call: Call<ResLoginDto>, t: Throwable) {
                    Timber.e(t)
                }
            })
    }

    private fun handleAutoLogin() {
        val loggedIn = GoSoptApplication.prefs.getBoolean(LOGIN_STATUS_KEY, false)
        if (loggedIn) {
            navigateToMainScreen()
        }
    }

    private fun navigateToMainScreen() {
        Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(this)
        }
    }

    private fun handleLoginRetrofitResponse(response: Response<ResLoginDto>) {
        if (response.isSuccessful) {
            response.body()?.let {
                Timber.d(it.status.toString())
                Timber.d(it.message)
                Timber.d(it.data.id)
                Timber.d(it.data.name)
                Timber.d(it.data.skill)
            }
        } else {
            Timber.e(response.code().toString())
        }
    }

    private fun checkInputValidity(): Boolean {
        val id = binding.etId.text.toString()
        val pw = binding.etPw.text.toString()
        return id.isNotBlank() &&
                pw.isNotBlank() &&
                checkLengthOfId(id) &&
                checkLengthOfPw(pw)
    }

    private fun checkLengthOfId(id: String): Boolean {
        return id.length in ID_MIN_LEN..ID_MAX_LEN
    }

    private fun checkLengthOfPw(pw: String): Boolean {
        return pw.length in PW_MIN_LEN..PW_MAX_LEN
    }

    private fun initSignUpButtonClickListener() {
        binding.btnSignUp.setOnClickListener {
            initEditText(it)
            navigateToSignUpScreen()
        }
    }

    private fun navigateToSignUpScreen() {
        Intent(this, SignUpActivity::class.java).apply {
            startActivity(this)
        }
    }

    private fun initEditText(button: View?) {
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

    private fun initRootLayoutClickListener() {
        binding.root.setOnClickListener {
            hideKeyboard()
        }
    }

    companion object {
        private const val ID_MIN_LEN = 6
        private const val ID_MAX_LEN = 10
        private const val PW_MIN_LEN = 8
        private const val PW_MAX_LEN = 12
        private const val LOGIN_STATUS_KEY = "login"
    }
}

