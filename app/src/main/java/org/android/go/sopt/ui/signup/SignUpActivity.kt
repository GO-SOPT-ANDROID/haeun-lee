package org.android.go.sopt.ui.signup

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import org.android.go.sopt.R
import org.android.go.sopt.binding.BindingActivity
import org.android.go.sopt.databinding.ActivitySignUpBinding
import org.android.go.sopt.model.User
import org.android.go.sopt.ui.login.LoginActivity
import org.android.go.sopt.util.*
import org.android.go.sopt.util.extension.hideKeyboard
import org.android.go.sopt.util.extension.showToast

class SignUpActivity : BindingActivity<ActivitySignUpBinding>(R.layout.activity_sign_up) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initRootLayoutClickListener()
        initEditTextChangedListeners()
        initSignUpButtonClickListener()
    }

    private fun initSignUpButtonClickListener() {
        binding.btnSignUp.setOnClickListener {
            val userInfo = getUserInfoInputValues()
            if (checkSignUpInputValidity(userInfo)) {
                sendUserInfoToLoginScreen(userInfo)
                return@setOnClickListener
            }

            showToast(getString(R.string.sign_up_invalid_input_err))
        }
    }

    private fun sendUserInfoToLoginScreen(userInfo: User) {
        Intent(this, LoginActivity::class.java).apply {
            putExtra(ID_KEY, userInfo.id)
            putExtra(PW_KEY, userInfo.pw)
            putExtra(NAME_KEY, userInfo.name)
            putExtra(HOBBY_KEY, userInfo.hobby)
            setResult(RESULT_OK, this)
        }
        finish()
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

    private fun initRootLayoutClickListener() {
        binding.root.setOnClickListener {
            hideKeyboard()
        }
    }

    private fun checkSignUpInputValidity(userInfo: User): Boolean {
        return checkLengthOfId(userInfo.id) && checkLengthOfPw(userInfo.pw) &&
                userInfo.name.isNotEmpty() && userInfo.hobby.isNotEmpty()
    }

    private fun getUserInfoInputValues(): User {
        val id = binding.etId.text.toString()
        val pw = binding.etPw.text.toString()
        val name = binding.etName.text.toString()
        val hobby = binding.etHobby.text.toString()
        return User(id, pw, name, hobby)
    }

    private fun checkLengthOfId(id: String): Boolean {
        return id.length in ID_MIN_LEN..ID_MAX_LEN
    }

    private fun checkLengthOfPw(pw: String): Boolean {
        return pw.length in PW_MIN_LEN..PW_MAX_LEN
    }

    companion object {
        private const val ID_MIN_LEN = 6
        private const val ID_MAX_LEN = 10
        private const val PW_MIN_LEN = 8
        private const val PW_MAX_LEN = 12
    }
}