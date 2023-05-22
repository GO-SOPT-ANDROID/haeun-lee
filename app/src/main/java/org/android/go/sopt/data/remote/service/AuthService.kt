package org.android.go.sopt.data.remote.service

import org.android.go.sopt.data.remote.entity.request.RequestPostLoginDto
import org.android.go.sopt.data.remote.entity.request.RequestPostSignUpDto
import org.android.go.sopt.data.remote.entity.response.ResponsePostLoginDto
import org.android.go.sopt.data.remote.entity.response.base.BaseResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("sign-up")
    fun signUp(
        @Body request: RequestPostSignUpDto
    ): Call<BaseResponse<org.android.go.sopt.data.remote.entity.response.ResponsePostSignUpDto>>

    @POST("sign-in")
    fun login(
        @Body request: RequestPostLoginDto
    ): Call<BaseResponse<ResponsePostLoginDto>>
}

