package org.android.go.sopt.presentation.main.mypage

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import org.android.go.sopt.R
import org.android.go.sopt.util.binding.BindingFragment
import org.android.go.sopt.databinding.FragmentMyPageBinding
import org.android.go.sopt.presentation.login.LoginActivity
import org.android.go.sopt.util.PreferenceManager

class MyPageFragment : BindingFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {
    private val preferenceManager = PreferenceManager()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUserProfile()
        initLogoutButtonClickListener()
    }

    private fun initUserProfile() {
        val signedUpUser = preferenceManager.signedUpUser
        if (signedUpUser != null) {
            binding.tvName.append(signedUpUser.name)
            binding.tvHobby.append(signedUpUser.hobby)
        }
    }

    private fun initLogoutButtonClickListener() {
        binding.btnLogout.setOnClickListener {
            showAlertDialog()
        }
    }

    private fun showAlertDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage(R.string.dialog_logout_msg)
            .setPositiveButton(R.string.dialog_yes) { dialog, id ->
                preferenceManager.loginState = false
                navigateToLoginScreen()
            }
            .setNegativeButton(R.string.dialog_no, null)
            .create()
        builder.show()
    }

    private fun navigateToLoginScreen() {
        Intent(requireContext(), LoginActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(this)
        }
    }
}