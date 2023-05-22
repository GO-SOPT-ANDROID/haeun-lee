package org.android.go.sopt.data.remote.service

import org.android.go.sopt.data.remote.entity.request.RequestPostLoginDto
import org.android.go.sopt.data.remote.entity.request.RequestPostSignUpDto
import org.android.go.sopt.data.remote.entity.response.ResponsePostLoginDto
import org.android.go.sopt.data.remote.entity.response.ResponsePostSignUpDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthService {
    @POST("sign-up")
    fun signUp(
        @Body request: RequestPostSignUpDto
    ): Call<ResponsePostSignUpDto>

    @POST("sign-in")
    fun login(
        @Body request: RequestPostLoginDto
    ): Call<ResponsePostLoginDto>

    @GET("info/{userId}")
    fun getUserInfo(
        @Path("userId") id: String
    ): Call<ResponsePostLoginDto>
}

