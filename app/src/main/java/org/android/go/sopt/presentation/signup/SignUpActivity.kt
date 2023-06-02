package org.android.go.sopt.presentation.signup

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import org.android.go.sopt.R
import org.android.go.sopt.util.binding.BindingActivity
import org.android.go.sopt.databinding.ActivitySignUpBinding
import org.android.go.sopt.presentation.login.LoginActivity
import org.android.go.sopt.util.code.CODE_DUPLICATED_ID
import org.android.go.sopt.util.code.CODE_INVALID_INPUT
import org.android.go.sopt.util.extension.hideKeyboard
import org.android.go.sopt.util.extension.showSnackbar
import org.android.go.sopt.util.state.RemoteUiState.*

class SignUpActivity : BindingActivity<ActivitySignUpBinding>(R.layout.activity_sign_up) {
    private val viewModel by viewModels<SignUpViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel

        initRootLayoutClickListener()
        initEditTextChangedListener()
        initSignUpStateObserver()
    }

    private fun initEditTextChangedListener() {
        binding.etId.addTextChangedListener {
            if (!viewModel.isValidId()) {
                binding.tilId.error = getString(R.string.sign_up_id_helper_text)
                deactivateSignUpButton()
            } else {
                binding.tilId.error = null
                if (viewModel.isValidInput()) activateSignUpButton()
            }
        }

        binding.etPw.addTextChangedListener {
            if (!viewModel.isValidPw()) {
                binding.tilPw.error = getString(R.string.sign_up_pw_helper_text)
                deactivateSignUpButton()
            } else {
                binding.tilPw.error = null
                if (viewModel.isValidInput()) activateSignUpButton()
            }
        }

        binding.etName.addTextChangedListener {
            if (viewModel.isNotBlankName()) {
                binding.tilName.error = null
                if (viewModel.isValidInput()) activateSignUpButton()
            } else {
                binding.tilName.error = getString(R.string.sign_up_required_input_err)
                deactivateSignUpButton()
            }
        }

        binding.etHobby.addTextChangedListener {
            if (viewModel.isNotBlankHobby()) {
                binding.tilHobby.error = null
                if (viewModel.isValidInput()) activateSignUpButton()
            } else {
                binding.tilHobby.error = getString(R.string.sign_up_required_input_err)
                deactivateSignUpButton()
            }
        }
    }

    private fun activateSignUpButton() {
        binding.btnSignUp.isEnabled = true
    }

    private fun deactivateSignUpButton() {
        binding.btnSignUp.isEnabled = false
    }

    private fun initSignUpStateObserver() {
        viewModel.signUpState.observe(this) { state ->
            when (state) {
                is Success -> navigateToLoginScreen()
                is Failure -> {
                    when (state.code) {
                        CODE_INVALID_INPUT -> showSnackbar(
                            binding.root,
                            getString(R.string.invalid_input_error_msg)
                        )
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