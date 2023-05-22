package org.android.go.sopt.presentation.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.android.go.sopt.data.entity.remote.request.RequestPostSignUpDto
import org.android.go.sopt.domain.model.User
import org.android.go.sopt.domain.repository.AuthRepository
import org.android.go.sopt.util.code.CODE_DUPLICATED_ID
import org.android.go.sopt.util.code.CODE_INCORRECT_INPUT
import org.android.go.sopt.util.state.RemoteUiState
import org.android.go.sopt.util.state.RemoteUiState.*
import retrofit2.HttpException
import timber.log.Timber

class SignUpViewModel : ViewModel() {
    private val authRepository = AuthRepository()

    private val _signUpState = MutableLiveData<RemoteUiState>()
    val signUpState: LiveData<RemoteUiState>
        get() = _signUpState

    val _id = MutableLiveData("")
    private val id: String get() = _id.value?.trim() ?: ""

    val _pw = MutableLiveData("")
    private val pw: String get() = _pw.value?.trim() ?: ""

    val _name = MutableLiveData("")
    private val name: String get() = _name.value?.trim() ?: ""

    val _hobby = MutableLiveData("")
    private val hobby: String get() = _hobby.value?.trim() ?: ""

    private fun isValidInput(): Boolean {
        return isValidId() && isValidPw() &&
                name.isNotBlank() && hobby.isNotBlank()
    }

    private fun isValidId(): Boolean {
        return id.isNotBlank() && id.length in MIN_ID_LENGTH..MAX_ID_LENGTH
    }

    private fun isValidPw(): Boolean {
        return pw.isNotBlank() && pw.length in MIN_PW_LENGTH..MAX_PW_LENGTH
    }

    fun signUp() {
        if (!isValidInput()) {
            _signUpState.value = Failure(CODE_INCORRECT_INPUT)
            return
        }

        val requestPostSignUpDto = RequestPostSignUpDto(
            id = id,
            password = pw,
            name = name,
            skill = hobby
        )

        viewModelScope.launch {
            authRepository.postSignUp(requestPostSignUpDto)
                .onSuccess { response ->
                    saveUserToPref()
                    _signUpState.value = Success
                    Timber.d("POST SIGNUP SUCCESS : $response")
                }
                .onFailure { t ->
                    if (t is HttpException) {
                        when (t.code()) {
                            CODE_INCORRECT_INPUT -> _signUpState.value =
                                Failure(CODE_INCORRECT_INPUT)
                            CODE_DUPLICATED_ID -> _signUpState.value = Failure(CODE_DUPLICATED_ID)
                            else -> _signUpState.value = Error
                        }
                        Timber.e("POST SIGNUP FAIL ${t.code()} : ${t.message()}")
                    }
                }
        }
    }

    private fun saveUserToPref() {
        authRepository.setSignedUpUser(
            User(
                id = id,
                pw = pw,
                name = name,
                hobby = hobby
            )
        )
    }

    companion object {
        const val MIN_ID_LENGTH = 6
        const val MAX_ID_LENGTH = 10
        const val MIN_PW_LENGTH = 8
        const val MAX_PW_LENGTH = 12
    }
}