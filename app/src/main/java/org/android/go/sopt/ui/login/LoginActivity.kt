package org.android.go.sopt.ui.login

import android.content.Intent
import android.content.Intent.EXTRA_USER
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import org.android.go.sopt.R
import org.android.go.sopt.GoSoptApplication
import org.android.go.sopt.data.remote.AuthFactory
import org.android.go.sopt.data.remote.model.ReqLoginDto
import org.android.go.sopt.data.remote.model.ResLoginDto
import org.android.go.sopt.util.binding.BindingActivity
import org.android.go.sopt.databinding.ActivityLoginBinding
import org.android.go.sopt.domain.model.User
import org.android.go.sopt.ui.main.MainActivity
import org.android.go.sopt.ui.signup.SignUpActivity
import org.android.go.sopt.util.extension.getCompatibleParcelableExtra
import org.android.go.sopt.util.extension.hideKeyboard
import org.android.go.sopt.util.extension.showSnackbar
import org.android.go.sopt.util.extension.showToast
import retrofit2.Call
import retrofit2.Response
import timber.log.Timber

// TODO: 서버 데이터 사용해서 회원가입, 로그인, 로그아웃 하도록 변경
class LoginActivity : BindingActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private lateinit var signedUser: User
    private var signedUpStatus: Boolean = false

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
        val prefUser = GoSoptApplication.prefs.getUserData()
        if (prefUser != null) {
            return checkPrefUserData(prefUser)
        }
        return false
    }

    private fun checkPrefUserData(prefUser: User): Boolean {
        return prefUser.id.isNotBlank() &&
                prefUser.pw.isNotBlank() &&
                prefUser.name.isNotBlank() &&
                prefUser.hobby.isNotBlank()
    }

    private fun navigateToMainScreen() {
        Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(this)
        }
    }

    private fun initLoginButtonClickListener() {
        binding.btnLogin.setOnClickListener {
            if (signedUpStatus) {
                handleLoginInput()
            } else {
                showToast(getString(R.string.unregistered_msg))
            }
        }
    }

    private fun handleLoginInput() {
        if (checkLoginInputValidity()) {
            showToast(getString(R.string.login_success_msg))
            sendUserDataToServer()
            navigateToMainScreen()
        } else {
            showToast(getString(R.string.login_fail_msg))
        }
    }

    private fun sendUserDataToServer() {
        val loginService = AuthFactory.ServicePool.loginService
        val call = loginService.login(ReqLoginDto(signedUser.id, signedUser.pw))
        handleLoginRetrofitResult(call)
    }

    private fun handleLoginRetrofitResult(call: Call<ResLoginDto>) {
        call.enqueue(object : retrofit2.Callback<ResLoginDto> {
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

    private fun checkLoginInputValidity(): Boolean {
        val id = binding.etId.text.toString()
        val pw = binding.etPw.text.toString()
        return signedUser.id == id && signedUser.pw == pw
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
            initEditText(it)
            signUpResultLauncher.launch(
                Intent(this, SignUpActivity::class.java)
            )
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

    private fun handleSignUpResult(result: ActivityResult) {
        signedUpStatus = true
        showSnackbar(binding.root, getString(R.string.sign_up_success_msg))
        initUserInfoFromIntent(result.data)
        saveUserInfoToPrefs()
    }

    private fun initUserInfoFromIntent(intent: Intent?) {
        intent?.getCompatibleParcelableExtra<User>(EXTRA_USER)?.apply {
            signedUser = this
        }
    }

    private fun saveUserInfoToPrefs() {
        GoSoptApplication.prefs.putUserData(signedUser)
    }

    private fun initRootLayoutClickListener() {
        binding.root.setOnClickListener {
            hideKeyboard()
        }
    }
}

