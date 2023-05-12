package org.android.go.sopt.ui.signup

import android.content.Intent
import android.content.Intent.EXTRA_USER
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import org.android.go.sopt.R
import org.android.go.sopt.util.binding.BindingActivity
import org.android.go.sopt.data.remote.AuthFactory
import org.android.go.sopt.data.remote.model.ReqSignUpDto
import org.android.go.sopt.data.remote.model.ResSignUpDto
import org.android.go.sopt.databinding.ActivitySignUpBinding
import org.android.go.sopt.domain.model.User
import org.android.go.sopt.ui.login.LoginActivity
import org.android.go.sopt.util.extension.hideKeyboard
import org.android.go.sopt.util.extension.showToast
import retrofit2.Call
import retrofit2.Response
import timber.log.Timber

// TODO: id 중복 여부 체크하여 에러 메시지 띄우기
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
            initUserObjFromInput()

            if (checkSignUpInputValidity()) {
                registerUserToServer()
                sendUserExtraToLoginScreen()
                return@setOnClickListener
            }

            showToast(getString(R.string.sign_up_invalid_input_err))
        }
    }

    private fun registerUserToServer() {
        val signUpService = AuthFactory.ServicePool.signUpService
        val call = signUpService.signUp(ReqSignUpDto(user.id, user.pw, user.name, user.hobby))

        handleSignUpRetrofitResult(call)
    }

    private fun handleSignUpRetrofitResult(call: Call<ResSignUpDto>) {
        call.enqueue(object : retrofit2.Callback<ResSignUpDto> {
            // 응답이 온 경우
            override fun onResponse(
                call: Call<ResSignUpDto>,
                response: Response<ResSignUpDto>
            ) {
                handleSignUpRetrofitResponse(response)
            }

            // 응답이 안 온 경우
            override fun onFailure(call: Call<ResSignUpDto>, t: Throwable) {
                Timber.e(t)
            }
        })
    }

    private fun handleSignUpRetrofitResponse(response: Response<ResSignUpDto>) {
        if (response.isSuccessful) {
            response.body()?.let {
                Timber.d(it.status.toString())
                Timber.d(it.message)
                Timber.d(it.data.name)
                Timber.d(it.data.skill)
            }
        } else {
            if (response.code() == 409) {
                Timber.e(getString(R.string.id_duplicate_error_msg))
            }
        }
    }

    private fun sendUserExtraToLoginScreen() {
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

    private fun initUserObjFromInput() {
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
    }
}