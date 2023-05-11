package org.android.go.sopt.ui.signup

import android.content.Intent
import android.content.Intent.EXTRA_USER
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import org.android.go.sopt.R
import org.android.go.sopt.util.binding.BindingActivity
import org.android.go.sopt.data.remote.ApiFactory
import org.android.go.sopt.data.remote.model.RequestSignUpDto
import org.android.go.sopt.data.remote.model.ResponseSignUpDto
import org.android.go.sopt.databinding.ActivitySignUpBinding
import org.android.go.sopt.domain.model.User
import org.android.go.sopt.ui.login.LoginActivity
import org.android.go.sopt.util.extension.hideKeyboard
import org.android.go.sopt.util.extension.showToast
import retrofit2.Call
import retrofit2.Response

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
            initUserFromInput()

            if (checkSignUpInputValidity()) {
                registerUserToServer()
                sendUserDataToLoginScreen()
                return@setOnClickListener
            }

            showToast(getString(R.string.sign_up_invalid_input_err))
        }
    }

    private fun registerUserToServer() {
        val signUpService = ApiFactory.ServicePool.signUpService
        val call = signUpService.signUp(RequestSignUpDto(user.id, user.pw, user.name, user.hobby))
        handleRetrofitResult(call)
    }

    private fun handleRetrofitResult(call: Call<ResponseSignUpDto>) {
        call.enqueue(object : retrofit2.Callback<ResponseSignUpDto> {
            // 응답이 온 경우
            override fun onResponse(
                call: Call<ResponseSignUpDto>,
                response: Response<ResponseSignUpDto>
            ) {
                handleRetrofitResponse(response)
            }

            // 응답이 안 온 경우
            override fun onFailure(call: Call<ResponseSignUpDto>, t: Throwable) {
                t.message?.let {
                    Log.e(RETROFIT_TAG, it)
                }
            }
        })
    }

    private fun handleRetrofitResponse(response: Response<ResponseSignUpDto>) {
        // 서버 통신 성공
        if (response.isSuccessful) {
            response.body()?.message?.let {
                Log.d(RETROFIT_TAG, it)
            }
        } else {
            // 서버 통신 실패
            response.body()?.message?.let {
                Log.e(RETROFIT_TAG, it)
            }
        }
    }

    private fun sendUserDataToLoginScreen() {
        Intent(this, LoginActivity::class.java).apply {
            putExtra(EXTRA_USER, user)
            setResult(RESULT_OK, this)
        }
        finish()
    }

    private fun initRootLayoutClickListener() {
        binding.root.setOnClickListener {
            hideKeyboard()
        }
    }

    private fun checkSignUpInputValidity(): Boolean {
        return checkLengthOfId(user.id) &&
                checkLengthOfPw(user.pw) &&
                user.name.isNotBlank() &&
                user.hobby.isNotBlank()
    }

    private fun initUserFromInput() {
        val id = binding.etId.text.toString()
        val pw = binding.etPw.text.toString()
        val name = binding.etName.text.toString()
        val hobby = binding.etHobby.text.toString()
        user = User(id, pw, name, hobby)
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

        private const val RETROFIT_TAG = "Retrofit"
    }
}