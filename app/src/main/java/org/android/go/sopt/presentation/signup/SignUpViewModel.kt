package org.android.go.sopt.presentation.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.android.go.sopt.GoSoptApplication
import org.android.go.sopt.data.entity.remote.request.RequestPostSignUpDto
import org.android.go.sopt.domain.repository.AuthRepository
import org.android.go.sopt.util.state.RemoteUiState
import org.android.go.sopt.util.state.RemoteUiState.*
import retrofit2.HttpException
import timber.log.Timber

class SignUpViewModel : ViewModel() {
    private val authRepository = AuthRepository()

    private val _signUpState = MutableLiveData<RemoteUiState>()
    val signUpState: LiveData<RemoteUiState>
        get() = _signUpState

    private val _id = MutableLiveData("")
    val id: String get() = _id.value?.trim() ?: ""

    private val _pw = MutableLiveData("")
    val pw: String get() = _pw.value?.trim() ?: ""

    private val _name = MutableLiveData("")
    val name: String get() = _name.value?.trim() ?: ""

    private val _hobby = MutableLiveData("")
    val hobby: String get() = _hobby.value?.trim() ?: ""

    private fun isValidInput(): Boolean {
        return isValidId(id) &&
                isValidPw(pw) &&
                name.isNotBlank() &&
                hobby.isNotBlank()
    }

    private fun isValidId(id: String): Boolean {
        return id.isNotBlank() && id.length in MIN_ID_LENGTH..MAX_ID_LENGTH
    }

    private fun isValidPw(pw: String): Boolean {
        return pw.isNotBlank() && pw.length in MIN_PW_LENGTH..MAX_PW_LENGTH
    }

    fun postSignUp() {
        if(!isValidInput()){
            _signUpState.value = Failure(CODE_INVALID_INPUT)
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
                    saveUserToPrefs(requestPostSignUpDto)
                    _signUpState.value = Success
                    Timber.d("POST SIGNUP SUCCESS : $response")
                }
                .onFailure { t ->
                    if (t is HttpException) {
                        when (t.code()) {
                            CODE_INVALID_INPUT -> _signUpState.value = Failure(CODE_INVALID_INPUT)
                            CODE_DUPLICATED_ID -> _signUpState.value = Failure(CODE_DUPLICATED_ID)
                            else -> _signUpState.value = Error
                        }
                        Timber.e("POST SIGNUP FAIL ${t.code()} : ${t.message()}")
                    }
                }
        }
    }

    private fun saveUserToPrefs(requestPostSignUpDto: RequestPostSignUpDto) {
        val user = requestPostSignUpDto.toUser()
        GoSoptApplication.prefs.putUserData(user)
    }

    companion object {
        const val MIN_ID_LENGTH = 6
        const val MAX_ID_LENGTH = 10
        const val MIN_PW_LENGTH = 8
        const val MAX_PW_LENGTH = 12

        const val CODE_INVALID_INPUT = 400
        const val CODE_DUPLICATED_ID = 409
    }
}