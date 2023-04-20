package org.android.go.sopt.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import org.android.go.sopt.R
import org.android.go.sopt.binding.BindingActivity
import org.android.go.sopt.databinding.ActivityMainBinding
import org.android.go.sopt.ui.main.gallery.GalleryFragment
import org.android.go.sopt.ui.main.home.HomeFragment
import org.android.go.sopt.ui.main.search.SearchFragment

class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadHomeFragment()
        initBnvItemSelectedListener()
        initBnvItemReselectedListener()
    }

    private fun initBnvItemReselectedListener() {
        binding.bnvMain.setOnItemReselectedListener {
            val currentFragment = supportFragmentManager.findFragmentById(R.id.fcv_main)
            if(currentFragment is HomeFragment){
                val recyclerView = findViewById<RecyclerView>(R.id.rv_repo)
                recyclerView.scrollToPosition(0)
            }
        }
    }

    private fun initBnvItemSelectedListener() {
        binding.bnvMain.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home_menu -> {
                    changeFragment(HomeFragment())
                    true
                }
                R.id.gallery_menu -> {
                    changeFragment(GalleryFragment())
                    true
                }
                R.id.search_menu -> {
                    changeFragment(SearchFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun loadHomeFragment() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fcv_main)
        if (currentFragment == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fcv_main, HomeFragment())
                .commit()
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fcv_main, fragment)
            .commit()
    }

    companion object {
        private const val UP_DIRECTION = -1
    }
}