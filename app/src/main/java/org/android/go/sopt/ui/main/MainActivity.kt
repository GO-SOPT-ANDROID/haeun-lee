package org.android.go.sopt.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.android.go.sopt.Week1Application
import org.android.go.sopt.databinding.ActivityMainBinding
import org.android.go.sopt.util.HOBBY_KEY
import org.android.go.sopt.util.NAME_KEY

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = Week1Application.prefs.getString(NAME_KEY, "nothing")
        val hobby = Week1Application.prefs.getString(HOBBY_KEY, "nothing")
        binding.tvName.text = "이름: $name"
        binding.tvHobby.text = "특기: $hobby"
    }
}