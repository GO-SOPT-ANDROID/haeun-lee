package org.android.go.sopt.ui.main.mypage

import android.os.Bundle
import android.view.View
import org.android.go.sopt.GoSoptApplication
import org.android.go.sopt.R
import org.android.go.sopt.binding.BindingFragment
import org.android.go.sopt.databinding.FragmentMyPageBinding

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
            // todo: 유저 데이터 삭제하고, 로그인 화면 다시 진입하기
        }
    }

    companion object {
        private const val NAME_KEY = "name"
        private const val HOBBY_KEY = "hobby"
    }
}