package org.android.go.sopt.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import org.android.go.sopt.R
import org.android.go.sopt.databinding.ActivityMainBinding
import org.android.go.sopt.ui.main.bnv.GalleryFragment
import org.android.go.sopt.ui.main.bnv.HomeFragment
import org.android.go.sopt.ui.main.bnv.SearchFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fcv_main)
        if(currentFragment == null){
            supportFragmentManager.beginTransaction()
                .add(R.id.fcv_main, HomeFragment())
                .commit()
        }

        binding.bnvMain.setOnItemSelectedListener { item ->
            when(item.itemId){
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