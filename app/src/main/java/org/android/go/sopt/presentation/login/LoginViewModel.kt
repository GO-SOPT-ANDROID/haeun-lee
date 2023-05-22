package org.android.go.sopt.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.android.go.sopt.data.entity.remote.request.RequestPostLoginDto
import org.android.go.sopt.domain.repository.AuthRepository
import org.android.go.sopt.util.state.RemoteUiState
import org.android.go.sopt.util.state.RemoteUiState.*
import retrofit2.HttpException
import timber.log.Timber

class LoginViewModel : ViewModel() {
    private val authRepository = AuthRepository()

    private val _loginState = MutableLiveData<RemoteUiState>()
    val loginState: LiveData<RemoteUiState>
        get() = _loginState

    private val _id = MutableLiveData("")
    val id: String get() = _id.value?.trim() ?: ""

    private val _pw = MutableLiveData("")
    val pw: String get() = _pw.value?.trim() ?: ""

    init {
        // todo: 자동 로그인

    }

    fun postLogin() {
        val requestPostLoginDto = RequestPostLoginDto(
            id = id,
            password = pw
        )

        viewModelScope.launch {
            authRepository.postLogin(requestPostLoginDto)
                .onSuccess { response ->
                    // todo: 로그인 성공 로직 처리

                    Timber.d("POST LOGIN SUCCESS : $response")
                }
                .onFailure { t ->
                    if (t is HttpException) {
                        when (t.code()) {
                            CODE_INVALID_INPUT -> _loginState.value = Failure(CODE_INVALID_INPUT)
                            else -> _loginState.value = Error
                        }

                        Timber.e("POST LOGIN FAIL ${t.code()} : ${t.message()}")
                    }
                }
        }
    }

    companion object {
        private const val CODE_INVALID_INPUT = 400
    }
}