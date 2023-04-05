package org.android.go.sopt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import org.android.go.sopt.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.etId.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!checkValidityOfId(s.toString())){
                    binding.tvIdLimitError.visibility = View.VISIBLE
                }else{
                    binding.tvIdLimitError.visibility = View.INVISIBLE
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.etPw.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!checkValidityOfPw(s.toString())){
                    binding.tvPwLimitError.visibility = View.VISIBLE
                }else{
                    binding.tvPwLimitError.visibility = View.INVISIBLE
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.btnSignUp.setOnClickListener {
            val id = binding.etId.text.toString()
            val pw = binding.etPw.text.toString()
            val name = binding.etName.text.toString()
            val hobby = binding.etHobby.text.toString()
        }
    }

    private fun checkValidityOfId(id: String): Boolean {
        return id.length in 6..10
    }

    private fun checkValidityOfPw(pw: String): Boolean {
        return pw.length in 8..12
    }
}