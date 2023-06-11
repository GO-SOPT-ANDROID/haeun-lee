package org.android.go.sopt.presentation.signup

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import org.android.go.sopt.R
import org.android.go.sopt.util.binding.BindingActivity
import org.android.go.sopt.databinding.ActivitySignUpBinding
import org.android.go.sopt.presentation.login.LoginActivity
import org.android.go.sopt.util.code.CODE_DUPLICATED_ID
import org.android.go.sopt.util.extension.hideKeyboard
import org.android.go.sopt.util.extension.showSnackbar
import org.android.go.sopt.util.state.RemoteUiState.*

class SignUpActivity : BindingActivity<ActivitySignUpBinding>(R.layout.activity_sign_up) {
    private val viewModel by viewModels<SignUpViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel

        initRootLayoutClickListener()
        initSignUpStateObserver()
    }

    private fun initSignUpStateObserver() {
        viewModel.signUpState.observe(this) { state ->
            when (state) {
                is Success -> navigateToLoginScreen()
                is Failure -> {
                    when (state.code) {
                        CODE_DUPLICATED_ID -> showSnackbar(
                            binding.root,
                            getString(R.string.id_duplicate_error_msg)
                        )
                    }
                }
                is Error -> showSnackbar(binding.root, getString(R.string.network_error_msg))
            }
        }
    }

    private fun navigateToLoginScreen() {
        Intent(this, LoginActivity::class.java).apply {
            setResult(RESULT_OK, this)
            if (!isFinishing) finish()
        }
    }

    private fun initRootLayoutClickListener() {
        binding.root.setOnClickListener {
            hideKeyboard()
            focusOutEditText()
        }
    }

    private fun focusOutEditText() {
        with(binding) {
            etId.clearFocus()
            etPw.clearFocus()
            etName.clearFocus()
            etHobby.clearFocus()
        }
    }
}