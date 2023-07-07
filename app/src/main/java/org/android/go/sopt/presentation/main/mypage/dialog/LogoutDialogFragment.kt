package org.android.go.sopt.presentation.main.mypage.dialog

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import org.android.go.sopt.R
import org.android.go.sopt.databinding.FragmentLogoutDialogBinding
import org.android.go.sopt.presentation.login.LoginActivity
import org.android.go.sopt.presentation.main.mypage.MyPageViewModel
import org.android.go.sopt.util.binding.BindingDialogFragment

class LogoutDialogFragment: BindingDialogFragment<FragmentLogoutDialogBinding>(R.layout.fragment_logout_dialog) {
    private val viewModel by viewModels<MyPageViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel

        initCancelButtonClickListener()
        initConfirmButtonClickListener()
    }

    private fun initCancelButtonClickListener() {
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }

    private fun initConfirmButtonClickListener() {
        binding.btnConfirm.setOnClickListener {
            viewModel.logout()
            navigateToLoginScreen()
        }
    }

    private fun navigateToLoginScreen() {
        Intent(requireContext(), LoginActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(this)
        }
    }
}