package org.android.go.sopt.presentation.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import org.android.go.sopt.R
import org.android.go.sopt.databinding.ActivityLoginBinding
import org.android.go.sopt.presentation.main.MainActivity
import org.android.go.sopt.presentation.signup.SignUpActivity
import org.android.go.sopt.presentation.dialog.LoadingDialogFragment
import org.android.go.sopt.presentation.dialog.LoadingDialogFragment.Companion.TAG_LOADING_DIALOG
import org.android.go.sopt.util.binding.BindingActivity
import org.android.go.sopt.util.code.CODE_INCORRECT_INPUT
import org.android.go.sopt.util.code.CODE_INVALID_INPUT
import org.android.go.sopt.util.code.CODE_UNREGISTERED_USER
import org.android.go.sopt.util.extension.hideKeyboard
import org.android.go.sopt.util.extension.showSnackbar
import org.android.go.sopt.util.state.RemoteUiState.*

class LoginActivity : BindingActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private val viewModel by viewModels<LoginViewModel>()
    private val loadingDialog by lazy { LoadingDialogFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel

        initRootLayoutClickListener()
        initSignUpButtonClickListener()
        initLoginStateObserver()
    }

    private fun initLoginStateObserver() {
        viewModel.loginState.observe(this) { state ->
            when (state) {
                is Loading -> loadingDialog.show(supportFragmentManager, TAG_LOADING_DIALOG)
                is Success -> {
                    loadingDialog.dismiss()
                    navigateToMainScreen()
                }
                is Failure -> {
                    loadingDialog.dismiss()
                    when (state.code) {
                        CODE_INVALID_INPUT -> showSnackbar(
                            binding.root,
                            getString(R.string.invalid_input_error_msg)
                        )
                        CODE_UNREGISTERED_USER -> showSnackbar(
                            binding.root,
                            getString(R.string.unregistered_user_error_msg)
                        )
                        CODE_INCORRECT_INPUT -> showSnackbar(
                            binding.root,
                            getString(R.string.incorrect_input_error_msg)
                        )
                    }
                }
                is Error -> showSnackbar(binding.root, getString(R.string.network_error_msg))
            }
        }
    }

    private fun navigateToMainScreen() {
        Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(this)
        }
    }

    private fun initSignUpButtonClickListener() {
        val signUpResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                showSnackbar(binding.root, getString(R.string.sign_up_success_msg))
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
        binding.etId.text?.clear()
        binding.etPw.text?.clear()
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
}