package org.android.go.sopt.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.android.go.sopt.R
import org.android.go.sopt.data.remote.AuthFactory
import org.android.go.sopt.data.remote.model.ReqSignUpDto
import org.android.go.sopt.data.remote.model.ResSignUpDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class SignUpViewModel : ViewModel() {
    private val _signUpResult = MutableLiveData<ResSignUpDto>()
    val signUpResult: LiveData<ResSignUpDto>
        get() = _signUpResult

    fun signUp(reqSignUpDto: ReqSignUpDto) {
        AuthFactory.ServicePool.authService.signUp(reqSignUpDto)
            .enqueue(object : Callback<ResSignUpDto> {
                override fun onResponse(
                    call: Call<ResSignUpDto>,
                    response: Response<ResSignUpDto>
                ) {
                    if (response.isSuccessful) {
                        _signUpResult.value = response.body()
                    } else {
                        Timber.e("error status code: ${response.code()}")

//                        if (response.code() == CODE_DUPLICATE_ID) {
//                            showSnackbar(binding.root, getString(R.string.id_duplicate_error_msg))
//                        }
                    }
                }

                override fun onFailure(call: Call<ResSignUpDto>, t: Throwable) {
                    Timber.e("error message: ${t.message}")
                }
            })
    }

    companion object {
        private const val CODE_DUPLICATE_ID = 409
    }
}