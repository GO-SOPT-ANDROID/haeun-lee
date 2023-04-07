package org.android.go.sopt.ui.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import org.android.go.sopt.ui.main.MainActivity
import org.android.go.sopt.Week1Application
import org.android.go.sopt.ui.signup.SignUpActivity
import org.android.go.sopt.model.User
import org.android.go.sopt.databinding.ActivityLoginBinding
import org.android.go.sopt.util.*

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private lateinit var userInfo: User
    private var userRegisteredStatus: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // SharedPreferences에 저장된 유저 정보가 있으면 메인 화면으로 진입
        if (isLastUserLoggedIn()) {
            navigateToMainScreen()
        }

        binding.root.setOnClickListener {
            hideKeyboard()
        }

        // 회원가입 화면으로부터 유저 정보 가져오기
        resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                Snackbar.make(binding.root, SIGN_UP_SUCCESS_MSG, Snackbar.LENGTH_SHORT).show()
                registerUserInfo(result.data)

                // 자동 로그인을 위해 SharedPreferences에 유저 정보 저장하기
                saveUserInfoToPrefs()
            }
        }

        binding.btnLogin.setOnClickListener {
            if (userRegisteredStatus) {
                if (checkInputValues()) {
                    Toast.makeText(this, LOGIN_SUCCESS_MSG, Toast.LENGTH_SHORT).show()
                    navigateToMainScreen()
                } else {
                    Toast.makeText(this, LOGIN_FAIL_MSG, Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, NOT_YET_REGISTERED_MSG, Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            resultLauncher.launch(intent)
        }
    }

    private fun isLastUserLoggedIn(): Boolean {
        val id = Week1Application.prefs.getString(ID_KEY, null)
        val pw = Week1Application.prefs.getString(PW_KEY, null)
        val name = Week1Application.prefs.getString(NAME_KEY, null)
        val hobby = Week1Application.prefs.getString(HOBBY_KEY, null)
        return id != null && pw != null && name != null && hobby != null
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }

    private fun navigateToMainScreen() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun checkInputValues(): Boolean {
        val id = binding.etId.text.toString()
        val pw = binding.etPw.text.toString()
        return userInfo.id == id && userInfo.pw == pw
    }

    private fun saveUserInfoToPrefs() {
        Week1Application.prefs.setString(ID_KEY, userInfo.id)
        Week1Application.prefs.setString(PW_KEY, userInfo.pw)
        Week1Application.prefs.setString(NAME_KEY, userInfo.name)
        Week1Application.prefs.setString(HOBBY_KEY, userInfo.hobby)
    }

    private fun registerUserInfo(intent: Intent?) {
        userRegisteredStatus = true
        val id = intent?.getStringExtra(ID_KEY).toString()
        val pw = intent?.getStringExtra(PW_KEY).toString()
        val name = intent?.getStringExtra(NAME_KEY).toString()
        val hobby = intent?.getStringExtra(HOBBY_KEY).toString()
        userInfo = User(id, pw, name, hobby)
    }
}

