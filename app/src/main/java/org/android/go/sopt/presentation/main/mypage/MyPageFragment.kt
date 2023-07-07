package org.android.go.sopt.presentation.main.mypage

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import org.android.go.sopt.R
import org.android.go.sopt.databinding.FragmentMyPageBinding
import org.android.go.sopt.presentation.main.mypage.dialog.LogoutDialogFragment
import org.android.go.sopt.util.binding.BindingFragment

class MyPageFragment : BindingFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {
    private val viewModel by viewModels<MyPageViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        initLogoutButtonClickListener()
    }

    private fun initLogoutButtonClickListener() {
        binding.btnLogout.setOnClickListener {
            LogoutDialogFragment().show(parentFragmentManager, TAG_LOGOUT_DIALOG)
        }
    }

    companion object {
        private const val TAG_LOGOUT_DIALOG = "LOGOUT_DIALOG"
    }
}