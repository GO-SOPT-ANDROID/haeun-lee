package org.android.go.sopt.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.android.go.sopt.data.api.AuthFactory.ServicePool.authService
import org.android.go.sopt.data.model.remote.request.RequestPostLoginDto
import org.android.go.sopt.data.model.remote.response.ResponsePostLoginDto
import org.android.go.sopt.data.model.remote.response.base.BaseResponse
import org.android.go.sopt.presentation.signup.SignUpViewModel.Companion.MAX_ID_LENGTH
import org.android.go.sopt.presentation.signup.SignUpViewModel.Companion.MAX_PW_LENGTH
import org.android.go.sopt.presentation.signup.SignUpViewModel.Companion.MIN_ID_LENGTH
import org.android.go.sopt.presentation.signup.SignUpViewModel.Companion.MIN_PW_LENGTH
import org.android.go.sopt.util.PreferenceManager
import org.android.go.sopt.util.code.CODE_INCORRECT_INPUT
import org.android.go.sopt.util.code.CODE_INVALID_INPUT
import org.android.go.sopt.util.code.CODE_UNREGISTERED_USER
import org.android.go.sopt.util.state.RemoteUiState
import org.android.go.sopt.util.state.RemoteUiState.*
import retrofit2.HttpException
import timber.log.Timber

class LoginViewModel : ViewModel() {
    private val preferenceManager = PreferenceManager()

    private val _loginState = MutableLiveData<RemoteUiState>()
    val loginState: LiveData<RemoteUiState>
        get() = _loginState

    val _id = MutableLiveData("")
    private val id: String get() = _id.value?.trim() ?: ""

    val _pw = MutableLiveData("")
    private val pw: String get() = _pw.value?.trim() ?: ""

    init {
        handleAutoLogin()
    }

    private fun handleAutoLogin() {
        if (preferenceManager.loginState) {
            _loginState.value = Success
        }
    }

    private fun isValidInput() = isValidId() && isValidPw()

    private fun isValidId(): Boolean {
        return id.isNotBlank() && id.length in MIN_ID_LENGTH..MAX_ID_LENGTH
    }

    private fun isValidPw(): Boolean {
        return pw.isNotBlank() && pw.length in MIN_PW_LENGTH..MAX_PW_LENGTH
    }

    private fun existSignedUpUser(): Boolean {
        return preferenceManager.signedUpUser != null
    }

    private fun isCorrectInput(): Boolean {
        val signedUpUser = preferenceManager.signedUpUser
        return id == signedUpUser?.id && pw == signedUpUser.pw
    }

    fun login() {
        // 1. 입력값이 유효한지
        if (!isValidInput()) {
            _loginState.value = Failure(CODE_INVALID_INPUT)
            return
        }

        // 2. prefs에 저장된 데이터가 있는지
        if (!existSignedUpUser()) {
            _loginState.value = Failure(CODE_UNREGISTERED_USER)
            return
        }

        // 3. prefs에 저장된 아이디, 비밀번호와 일치하는지
        if (!isCorrectInput()) {
            _loginState.value = Failure(CODE_INCORRECT_INPUT)
            return
        }

        val requestPostLoginDto = RequestPostLoginDto(
            id = id,
            password = pw
        )

        viewModelScope.launch {
            postLoginResult(requestPostLoginDto)
                .onSuccess { response ->
                    preferenceManager.loginState = true
                    _loginState.value = Success
                    Timber.d("POST LOGIN SUCCESS : $response")
                }
                .onFailure { t ->
                    if (t is HttpException) {
                        when (t.code()) {
                            CODE_INCORRECT_INPUT -> _loginState.value = Failure(CODE_INCORRECT_INPUT)
                            else -> _loginState.value = Error
                        }
                        Timber.e("POST LOGIN FAIL ${t.code()} : ${t.message()}")
                    }
                }
        }
    }

    private suspend fun postLoginResult(requestPostLoginDto: RequestPostLoginDto): Result<ResponsePostLoginDto?> =
        runCatching {
            postLogin(requestPostLoginDto).data
        }

    private suspend fun postLogin(
        requestPostLoginDto: RequestPostLoginDto
    ): BaseResponse<ResponsePostLoginDto> = authService.postLogin(requestPostLoginDto)
}