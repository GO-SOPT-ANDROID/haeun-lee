package org.android.go.sopt.data.remote.service

import org.android.go.sopt.data.remote.model.RequestLoginDto
import org.android.go.sopt.data.remote.model.RequestSignUpDto
import org.android.go.sopt.data.remote.model.ResponseLoginDto
import org.android.go.sopt.data.remote.model.ResponseSignUpDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SignUpService {
    @POST("sign-up")
    fun signUp(
        @Body request: RequestSignUpDto
    ): Call<ResponseSignUpDto>
}

interface LoginService {
    @POST("sign-in")
    fun login(
        @Body request: RequestLoginDto
    ): Call<ResponseLoginDto>
}

interface UserInfoService {
    @GET("info/{userId}")
    fun getUserInfo(
        @Path("userId") id: String
    ): Call<ResponseLoginDto>
}