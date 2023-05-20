package org.android.go.sopt.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.android.go.sopt.data.remote.AuthFactory
import org.android.go.sopt.data.remote.model.ReqLoginDto
import org.android.go.sopt.data.remote.model.ResLoginDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class LoginViewModel : ViewModel() {
    private val _loginResult = MutableLiveData<ResLoginDto>()
    val loginResult: LiveData<ResLoginDto>
        get() = _loginResult

    fun login(reqLoginDto: ReqLoginDto) {
        AuthFactory.ServicePool.authService.login(reqLoginDto)
            .enqueue(object : Callback<ResLoginDto> {
                override fun onResponse(call: Call<ResLoginDto>, response: Response<ResLoginDto>) {
                    if (response.isSuccessful) {
                        _loginResult.value = response.body()
                    } else {
                        Timber.e("error status code: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<ResLoginDto>, t: Throwable) {
                    Timber.e("error message: ${t.message}")
                }
            })
    }
}