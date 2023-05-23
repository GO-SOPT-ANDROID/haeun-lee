package org.android.go.sopt.data.source.remote

import org.android.go.sopt.data.entity.remote.request.RequestPostLoginDto
import org.android.go.sopt.data.entity.remote.request.RequestPostSignUpDto
import org.android.go.sopt.data.entity.remote.response.ResponsePostLoginDto
import org.android.go.sopt.data.entity.remote.response.ResponsePostSignUpDto
import org.android.go.sopt.data.entity.remote.response.base.BaseResponse
import org.android.go.sopt.data.AuthFactory

/** 레트로핏 서버 통신 */
class AuthDataSource {
    private val authService = AuthFactory.ServicePool.authService

    suspend fun postSignUp(
        requestPostSignUpDto: RequestPostSignUpDto
    ): BaseResponse<ResponsePostSignUpDto> = authService.postSignUp(requestPostSignUpDto)

    suspend fun postLogin(
        requestPostLoginDto: RequestPostLoginDto
    ): BaseResponse<ResponsePostLoginDto> = authService.postLogin(requestPostLoginDto)
}