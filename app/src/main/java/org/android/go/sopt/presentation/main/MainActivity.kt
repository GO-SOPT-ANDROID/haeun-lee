package org.android.go.sopt.presentation.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import org.android.go.sopt.R
import org.android.go.sopt.util.binding.BindingActivity
import org.android.go.sopt.databinding.ActivityMainBinding
import org.android.go.sopt.presentation.main.gallery.GalleryFragment
import org.android.go.sopt.presentation.main.home.HomeFragment
import org.android.go.sopt.presentation.main.mypage.MyPageFragment
import org.android.go.sopt.presentation.main.search.SearchFragment

class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initDefaultFragment()
        initBnvItemSelectedListener()
        initBnvItemReselectedListener()
    }

    private fun initDefaultFragment() {
        supportFragmentManager.findFragmentById(R.id.fcv_main)
            ?: navigateTo<HomeFragment>()
    }

    private fun initBnvItemSelectedListener() {
        binding.bnvMain.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home_menu -> navigateTo<HomeFragment>()
                R.id.gallery_menu -> navigateTo<GalleryFragment>()
                R.id.search_menu -> navigateTo<SearchFragment>()
                R.id.mypage_menu -> navigateTo<MyPageFragment>()
            }
            true
        }
    }

    private fun initBnvItemReselectedListener() {
        binding.bnvMain.setOnItemReselectedListener {
            when (val currentFragment = supportFragmentManager.findFragmentById(R.id.fcv_main)) {
                is HomeFragment -> {
                    currentFragment.scrollToTop()
                }
                is GalleryFragment -> {
                    currentFragment.scrollToTop()
                }
            }
        }
    }

    // 불필요한 객체 생성을 막기 위해 inline 사용 & 제네릭 타입을 위해 reified 사용
    private inline fun <reified T : Fragment> navigateTo() {
        supportFragmentManager.commit {
            replace<T>(R.id.fcv_main, T::class.simpleName)
        }
    }
}