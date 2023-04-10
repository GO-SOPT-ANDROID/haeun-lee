package org.android.go.sopt.ui.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import org.android.go.sopt.ui.main.MainActivity
import org.android.go.sopt.Week1Application
import org.android.go.sopt.ui.signup.SignUpActivity
import org.android.go.sopt.model.User
import org.android.go.sopt.databinding.ActivityLoginBinding
import org.android.go.sopt.util.*
import org.android.go.sopt.util.extension.hideKeyboard

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var userInfo: User
    private var userRegisteredStatus: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (isLastUserLoggedIn()) {
            navigateToMainScreen()
        }

        initRootLayoutClickListener()

        initSignUpButtonClickListener()

        initLoginButtonClickListener()
    }

    private fun initLoginButtonClickListener() {
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
    }

    private fun initSignUpButtonClickListener() {
        val signUpResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                Snackbar.make(binding.root, SIGN_UP_SUCCESS_MSG, Snackbar.LENGTH_SHORT).show()
                registerUserInfo(result.data)
                saveUserInfoToPrefs()
            }
        }

        binding.btnSignUp.setOnClickListener {
            signUpResultLauncher.launch(
                Intent(this, SignUpActivity::class.java)
            )
        }
    }

    private fun initRootLayoutClickListener() {
        binding.root.setOnClickListener {
            hideKeyboard()
        }
    }

    private fun isLastUserLoggedIn(): Boolean {
        val id = Week1Application.prefs.getString(ID_KEY, null)
        val pw = Week1Application.prefs.getString(PW_KEY, null)
        val name = Week1Application.prefs.getString(NAME_KEY, null)
        val hobby = Week1Application.prefs.getString(HOBBY_KEY, null)
        return id != null && pw != null && name != null && hobby != null
    }

    private fun navigateToMainScreen() {
        Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(this)
        }
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

