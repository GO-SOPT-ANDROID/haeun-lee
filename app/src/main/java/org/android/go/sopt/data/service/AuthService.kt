package org.android.go.sopt.data.service

import org.android.go.sopt.data.entity.remote.request.RequestPostLoginDto
import org.android.go.sopt.data.entity.remote.request.RequestPostSignUpDto
import org.android.go.sopt.data.entity.remote.response.ResponsePostLoginDto
import org.android.go.sopt.data.entity.remote.response.ResponsePostSignUpDto
import org.android.go.sopt.data.entity.remote.response.base.BaseResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("sign-up")
    suspend fun postSignUp(
        @Body request: RequestPostSignUpDto
    ): BaseResponse<ResponsePostSignUpDto>

    @POST("sign-in")
    suspend fun postLogin(
        @Body request: RequestPostLoginDto
    ): BaseResponse<ResponsePostLoginDto>
}

