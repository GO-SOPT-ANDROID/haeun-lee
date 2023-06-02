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
import org.android.go.sopt.util.code.CODE_INCORRECT_INPUT
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
        initSignUpStateObserver()
        initEditTextChangedListener()
    }

    private fun initEditTextChangedListener() {
        binding.etId.addTextChangedListener {
            if(!viewModel.isValidId()){
                binding.tilId.error = getString(R.string.sign_up_id_helper_text)
            }else{
                binding.tilId.error = null
            }
        }

        binding.etPw.addTextChangedListener {
            if(!viewModel.isValidPw()){
                binding.tilPw.error = getString(R.string.sign_up_pw_helper_text)
            }else{
                binding.tilPw.error = null
            }
        }

        binding.etName.addTextChangedListener {
            if(viewModel.isNotBlankName()) {
                binding.tilName.error = null
            }else{
                binding.tilName.error = getString(R.string.sign_up_required_input_err)
            }
        }

        binding.etHobby.addTextChangedListener {
            if(viewModel.isNotBlankHobby()) {
                binding.tilHobby.error = null
            }else{
                binding.tilHobby.error = getString(R.string.sign_up_required_input_err)
            }
        }
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
                        CODE_INCORRECT_INPUT -> showSnackbar(
                            binding.root,
                            getString(R.string.incorrect_input_error_msg)
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
}