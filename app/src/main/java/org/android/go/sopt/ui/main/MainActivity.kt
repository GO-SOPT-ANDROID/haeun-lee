package org.android.go.sopt.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import org.android.go.sopt.R
import org.android.go.sopt.binding.BindingActivity
import org.android.go.sopt.databinding.ActivityMainBinding
import org.android.go.sopt.ui.main.bnv.GalleryFragment
import org.android.go.sopt.ui.main.bnv.HomeFragment
import org.android.go.sopt.ui.main.bnv.SearchFragment

class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fcv_main)
        if (currentFragment == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fcv_main, HomeFragment())
                .commit()
        }

        binding.bnvMain.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home_menu -> {
                    changeFragment(HomeFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.gallery_menu -> {
                    changeFragment(GalleryFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.search_menu -> {
                    changeFragment(SearchFragment())
                    return@setOnItemSelectedListener true
                }
                else -> false
            }
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fcv_main, fragment)
            .commit()
    }
}