package org.android.go.sopt

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.android.material.internal.ViewUtils.hideKeyboard
import org.android.go.sopt.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.root.setOnClickListener {
            hideKeyboard()
        }

        binding.etId.addTextChangedListener {
            if (!checkLengthOfId(it.toString())) {
                binding.tvIdLimitError.visibility = View.VISIBLE
            } else {
                binding.tvIdLimitError.visibility = View.INVISIBLE
            }
        }

        binding.etPw.addTextChangedListener {
            if (!checkLengthOfPw(it.toString())) {
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
            val inputs = getAllInputValues()
            if(checkInputValidity(inputs)){
                val intent = Intent(this, LoginActivity::class.java).apply {
                    putExtra("id", inputs[0])
                    putExtra("pw", inputs[1])
                    putExtra("name", inputs[2])
                    putExtra("hobby", inputs[3])
                }
                setResult(RESULT_OK, intent)
                finish()
            }else{
                Toast.makeText(this, "모든 항목에 유효한 값을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }

    private fun checkInputValidity(inputs: List<String>): Boolean {
        return checkLengthOfId(inputs[0]) && checkLengthOfPw(inputs[1]) && inputs[2].isNotEmpty() && inputs[3].isNotEmpty()
    }

    private fun getAllInputValues(): List<String> {
        val id = binding.etId.text.toString()
        val pw = binding.etPw.text.toString()
        val name = binding.etName.text.toString()
        val hobby = binding.etHobby.text.toString()
        return listOf(id, pw, name, hobby)
    }

    private fun checkLengthOfId(id: String): Boolean {
        return id.length in 6..10
    }

    private fun checkLengthOfPw(pw: String): Boolean {
        return pw.length in 8..12
    }
}