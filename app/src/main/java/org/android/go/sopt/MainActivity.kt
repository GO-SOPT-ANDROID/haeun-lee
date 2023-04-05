package org.android.go.sopt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import org.android.go.sopt.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var userInfo: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userInfo = intent.getSerializableExtra("user") as User
        Log.d(TAG, "${userInfo.id}, ${userInfo.pw}, ${userInfo.name}, ${userInfo.hobby}")
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}