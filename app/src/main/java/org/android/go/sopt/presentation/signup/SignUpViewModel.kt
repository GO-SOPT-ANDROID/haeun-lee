package org.android.go.sopt.presentation.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.android.go.sopt.data.api.AuthFactory.ServicePool.authService
import org.android.go.sopt.data.entity.User
import org.android.go.sopt.data.model.remote.request.RequestPostSignUpDto
import org.android.go.sopt.data.model.remote.response.ResponsePostSignUpDto
import org.android.go.sopt.data.model.remote.response.base.BaseResponse
import org.android.go.sopt.util.PreferenceManager
import org.android.go.sopt.util.code.CODE_DUPLICATED_ID
import org.android.go.sopt.util.code.CODE_INVALID_INPUT
import org.android.go.sopt.util.state.RemoteUiState
import org.android.go.sopt.util.state.RemoteUiState.*
import retrofit2.HttpException
import timber.log.Timber
import java.util.regex.Pattern

class SignUpViewModel : ViewModel() {
    private val _signUpState = MutableLiveData<RemoteUiState>()
    val signUpState: LiveData<RemoteUiState>
        get() = _signUpState

    // EditText에 입력된 값을 LiveData로 가져오는 양방향 데이터 바인딩을 위해
    // LiveData를 public으로 설정했다.
    val _id = MutableLiveData("")
    private val id: String get() = _id.value?.trim() ?: ""

    val _pw = MutableLiveData("")
    private val pw: String get() = _pw.value?.trim() ?: ""

    val _name = MutableLiveData("")
    private val name: String get() = _name.value?.trim() ?: ""

    val _hobby = MutableLiveData("")
    private val hobby: String get() = _hobby.value?.trim() ?: ""

    // 영문과 숫자를 적어도 하나씩 포함하는 6~10자리 문자열
    fun isValidId() = Pattern.matches(ID_REGEX, id)

    // 영문, 숫자, 특수문자를 적어도 하나씩 포함하는 6~12자리 문자열
    // 허용되는 특수 문자: !, @, #, $, %, ^, +, -, =
    fun isValidPw() = Pattern.matches(PW_REGEX, pw)

    fun isNotBlankName() = name.isNotBlank()

    fun isNotBlankHobby() = hobby.isNotBlank()

    fun isValidInput() = isValidId() && isValidPw() && isNotBlankName() && isNotBlankHobby()

    fun signUp() {
        if (!isValidInput()) {
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
            postSignUpResult(requestPostSignUpDto)
                .onSuccess { response ->
                    saveSignedUpUserToPrefs()
                    _signUpState.value = Success
                    Timber.d("POST SIGNUP SUCCESS : $response")
                }
                .onFailure { t ->
                    if (t is HttpException) {
                        when (t.code()) {
                            CODE_DUPLICATED_ID -> _signUpState.value = Failure(CODE_DUPLICATED_ID)
                            else -> _signUpState.value = Error
                        }
                        Timber.e("POST SIGNUP FAIL ${t.code()} : ${t.message()}")
                    } else {
                        _signUpState.value = Error
                        Timber.e("POST SIGNUP FAIL : ${t.message}")
                    }
                }
        }
    }

    private fun saveSignedUpUserToPrefs() {
        val preferenceManager = PreferenceManager()
        preferenceManager.signedUpUser = User(
            id = id,
            pw = pw,
            name = name,
            hobby = hobby
        )
    }

    private suspend fun postSignUp(
        requestPostSignUpDto: RequestPostSignUpDto
    ): BaseResponse<ResponsePostSignUpDto> = authService.postSignUp(requestPostSignUpDto)

    private suspend fun postSignUpResult(requestPostSignUpDto: RequestPostSignUpDto): Result<ResponsePostSignUpDto?> =
        runCatching {
            postSignUp(requestPostSignUpDto).data
        }

    companion object {
        const val MIN_ID_LENGTH = 6
        const val MAX_ID_LENGTH = 10
        const val MIN_PW_LENGTH = 6
        const val MAX_PW_LENGTH = 12
        
        private const val ID_REGEX = """^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{$MIN_ID_LENGTH,$MAX_ID_LENGTH}$"""
        private const val PW_REGEX = """^(?=.*[A-Za-z])(?=.*\d)(?=.*[!@#$%^+\-=])[A-Za-z\d!@#$%^+\-=]{$MIN_PW_LENGTH,$MAX_PW_LENGTH}$"""
    }
}