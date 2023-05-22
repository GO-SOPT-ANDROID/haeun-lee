package org.android.go.sopt.presentation.signup

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import org.android.go.sopt.R
import org.android.go.sopt.util.binding.BindingActivity
import org.android.go.sopt.databinding.ActivitySignUpBinding
import org.android.go.sopt.presentation.login.LoginActivity
import org.android.go.sopt.util.code.CODE_DUPLICATED_ID
import org.android.go.sopt.util.code.CODE_INCORRECT_INPUT
import org.android.go.sopt.util.extension.hideKeyboard
import org.android.go.sopt.util.extension.showSnackbar
import org.android.go.sopt.util.state.RemoteUiState.*

class SignUpActivity : BindingActivity<ActivitySignUpBinding>(R.layout.activity_sign_up) {
    private val viewModel by viewModels<SignUpViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel

        initRootLayoutClickListener()
        initEditTextChangedListeners()

        initSignUpStateObserver()
    }

    private fun initSignUpStateObserver() {
        viewModel.signUpState.observe(this) { state ->
            when (state) {
                is Success -> navigateToLoginScreen()
                is Failure -> {
                    when (state.code) {
                        CODE_INCORRECT_INPUT -> showSnackbar(
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
    }
}