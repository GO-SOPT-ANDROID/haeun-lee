package org.android.go.sopt

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import org.android.go.sopt.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.etId.addTextChangedListener {
            if (!checkValidityOfId(it.toString())) {
                binding.tvIdLimitError.visibility = View.VISIBLE
            } else {
                binding.tvIdLimitError.visibility = View.INVISIBLE
            }
        }

        binding.etPw.addTextChangedListener {
            if (!checkValidityOfPw(it.toString())) {
                binding.tvPwLimitError.visibility = View.VISIBLE
            } else {
                binding.tvPwLimitError.visibility = View.INVISIBLE
            }
        }

        binding.etName.addTextChangedListener {
            if (it.toString().isEmpty()) {
                binding.tvNameEmptyError.visibility = View.VISIBLE
            } else {
                binding.tvNameEmptyError.visibility = View.INVISIBLE
            }
        }

        binding.etHobby.addTextChangedListener {
            if (it.toString().isEmpty()) {
                binding.tvHobbyEmptyError.visibility = View.VISIBLE
            } else {
                binding.tvHobbyEmptyError.visibility = View.INVISIBLE
            }
        }

        binding.btnSignUp.setOnClickListener {
            val id = binding.etId.text.toString()
            val pw = binding.etPw.text.toString()
            val name = binding.etName.text.toString()
            val hobby = binding.etHobby.text.toString()

            if(checkValidityOfId(id) && checkValidityOfPw(pw) && name.isNotEmpty() && hobby.isNotEmpty()){
                val intent = Intent(this, LoginActivity::class.java).apply {
                    putExtra("id", id)
                    putExtra("pw", pw)
                    putExtra("name", name)
                    putExtra("hobby", hobby)
                }
                setResult(RESULT_OK, intent)
                finish()
            }else{
                Toast.makeText(this, "모든 항목에 유효한 값을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkValidityOfId(id: String): Boolean {
        return id.length in 6..10
    }

    private fun checkValidityOfPw(pw: String): Boolean {
        return pw.length in 8..12
    }
}