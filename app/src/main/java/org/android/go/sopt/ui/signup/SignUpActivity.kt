package org.android.go.sopt.ui.signup

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import org.android.go.sopt.GoSoptApplication
import org.android.go.sopt.R
import org.android.go.sopt.util.binding.BindingActivity
import org.android.go.sopt.data.remote.AuthFactory
import org.android.go.sopt.data.remote.model.ReqSignUpDto
import org.android.go.sopt.data.remote.model.ResSignUpDto
import org.android.go.sopt.databinding.ActivitySignUpBinding
import org.android.go.sopt.domain.model.User
import org.android.go.sopt.ui.login.LoginActivity
import org.android.go.sopt.util.extension.hideKeyboard
import org.android.go.sopt.util.extension.showSnackbar
import retrofit2.Call
import retrofit2.Response
import timber.log.Timber

class SignUpActivity : BindingActivity<ActivitySignUpBinding>(R.layout.activity_sign_up) {
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initRootLayoutClickListener()
        initEditTextChangedListeners()
        initSignUpButtonClickListener()
    }

    private fun initSignUpButtonClickListener() {
        binding.btnSignUp.setOnClickListener {
            if (checkInputValidity()) {
                createUserToServer()
                return@setOnClickListener
            }

            showSnackbar(binding.root, getString(R.string.invalid_input_error))
        }
    }

    private fun checkInputValidity(): Boolean {
        val id = binding.etId.text.toString()
        val pw = binding.etPw.text.toString()
        val name = binding.etName.text.toString()
        val hobby = binding.etHobby.text.toString()
        user = User(id, pw, name, hobby)

        return checkLengthOfId(user.id) &&
                checkLengthOfPw(user.pw) &&
                user.name.isNotBlank() &&
                user.hobby.isNotBlank()
    }

    private fun createUserToServer() {
        AuthFactory.ServicePool.authService.signUp(
            ReqSignUpDto(
                user.id,
                user.pw,
                user.name,
                user.hobby
            )
        ).enqueue(object : retrofit2.Callback<ResSignUpDto> {
            override fun onResponse(
                call: Call<ResSignUpDto>,
                response: Response<ResSignUpDto>
            ) {
                if (isResponseSuccessful(response)) {
                    saveUserToPrefs()
                    navigateToLoginScreen()
                }
            }

            override fun onFailure(call: Call<ResSignUpDto>, t: Throwable) {
                Timber.e(t)
            }
        })
    }

    private fun isResponseSuccessful(response: Response<ResSignUpDto>): Boolean {
        if (response.isSuccessful) {
            response.body()?.let {
                Timber.d(it.status.toString())
                Timber.d(it.message)
                Timber.d(it.data.name)
                Timber.d(it.data.skill)
            }
            return true
        }

        if (response.code() == CODE_DUPLICATE_ID) {
            showSnackbar(binding.root, getString(R.string.id_duplicate_error_msg))
        }

        return false
    }

    private fun saveUserToPrefs() {
        GoSoptApplication.prefs.putUserData(user)
    }

    private fun navigateToLoginScreen() {
        Intent(this, LoginActivity::class.java).apply {
            setResult(RESULT_OK, this)
        }
        finish()
    }

    private fun initRootLayoutClickListener() {
        binding.root.setOnClickListener {
            hideKeyboard()
        }
    }

    private fun checkLengthOfId(id: String): Boolean {
        return id.length in ID_MIN_LEN..ID_MAX_LEN
    }

    private fun checkLengthOfPw(pw: String): Boolean {
        return pw.length in PW_MIN_LEN..PW_MAX_LEN
    }

    private fun initEditTextChangedListeners() {
        binding.etId.addTextChangedListener {
            if (!checkLengthOfId(it.toString())) {
                binding.tvIdLimitError.visibility = View.VISIBLE
            } else {
                binding.tvIdLimitError.visibility = View.INVISIBLE
            }
        }

        binding.etPw.addTextChangedListener {
            if (!checkLengthOfPw(it.toString())) {
                binding.tvPwLimitError.visibility = View.VISIBLE
            } else {
                binding.tvPwLimitError.visibility = View.INVISIBLE
            }
        }

        binding.etName.addTextChangedListener {
            if (it.toString().isEmpty()) {
                binding.tvNameEmptyError.visibility = View.VISIBLE
            } else {
                binding.tvNameEmptyError.visibility = View.INVISIBLE
            }
        }

        binding.etHobby.addTextChangedListener {
            if (it.toString().isEmpty()) {
                binding.tvHobbyEmptyError.visibility = View.VISIBLE
            } else {
                binding.tvHobbyEmptyError.visibility = View.INVISIBLE
            }
        }
    }

    companion object {
        private const val ID_MIN_LEN = 6
        private const val ID_MAX_LEN = 10
        private const val PW_MIN_LEN = 8
        private const val PW_MAX_LEN = 12
        private const val CODE_DUPLICATE_ID = 409
    }
}