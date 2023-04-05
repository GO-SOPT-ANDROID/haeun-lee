package org.android.go.sopt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import org.android.go.sopt.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private lateinit var userInfo: User
    private var userRegisteredStatus: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                Snackbar.make(binding.root, "회원가입이 완료되었습니다.", Snackbar.LENGTH_SHORT).show()
                registerUserInfo(result.data)
            }
        }

        binding.btnLogin.setOnClickListener {
            if (userRegisteredStatus) {
                if (isRegisteredUser()) {
                    navigateToMainScreen()
                } else {
                    Toast.makeText(this, "등록된 유저 정보가 없습니다.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "회원가입을 먼저 진행해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            resultLauncher.launch(intent)
        }
    }

    private fun navigateToMainScreen() {
        Toast.makeText(this, "로그인에 성공했습니다.", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("user", userInfo)
        startActivity(intent)
    }

    private fun isRegisteredUser(): Boolean {
        val id = binding.etId.text.toString()
        val pw = binding.etPw.text.toString()
        return userInfo.id == id && userInfo.pw == pw
    }

    private fun registerUserInfo(intent: Intent?) {
        userRegisteredStatus = true
        val id = intent?.getStringExtra("id").toString()
        val pw = intent?.getStringExtra("pw").toString()
        val name = intent?.getStringExtra("name").toString()
        val hobby = intent?.getStringExtra("hobby").toString()
        userInfo = User(id, pw, name, hobby)
    }
}

