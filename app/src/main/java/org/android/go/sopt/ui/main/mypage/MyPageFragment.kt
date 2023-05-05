package org.android.go.sopt.ui.main.mypage

import android.content.Intent
import android.os.Bundle
import android.view.View
import org.android.go.sopt.GoSoptApplication
import org.android.go.sopt.R
import org.android.go.sopt.binding.BindingFragment
import org.android.go.sopt.databinding.FragmentMyPageBinding
import org.android.go.sopt.ui.login.LoginActivity
import org.android.go.sopt.ui.main.MainActivity

class MyPageFragment : BindingFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUserProfile()
        initLogoutButtonClickListener()
    }

    private fun initUserProfile() {
        val name = GoSoptApplication.prefs.getString(NAME_KEY, null)
        val hobby = GoSoptApplication.prefs.getString(HOBBY_KEY, null)
        binding.tvName.append(name)
        binding.tvHobby.append(hobby)
    }

    private fun initLogoutButtonClickListener() {
        binding.btnLogout.setOnClickListener {
            GoSoptApplication.prefs.deleteAllData()

            Intent(requireContext(), LoginActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(this)
            }
        }
    }

    companion object {
        private const val NAME_KEY = "name"
        private const val HOBBY_KEY = "hobby"
    }
}