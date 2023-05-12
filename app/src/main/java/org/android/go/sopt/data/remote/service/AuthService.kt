package org.android.go.sopt.data.remote.service

import org.android.go.sopt.data.remote.model.ReqLoginDto
import org.android.go.sopt.data.remote.model.ReqSignUpDto
import org.android.go.sopt.data.remote.model.ResLoginDto
import org.android.go.sopt.data.remote.model.ResSignUpDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthService {
    @POST("sign-up")
    fun signUp(
        @Body request: ReqSignUpDto
    ): Call<ResSignUpDto>

    @POST("sign-in")
    fun login(
        @Body request: ReqLoginDto
    ): Call<ResLoginDto>

    @GET("info/{userId}")
    fun getUserInfo(
        @Path("userId") id: String
    ): Call<ResLoginDto>
}

