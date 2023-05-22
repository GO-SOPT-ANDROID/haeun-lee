package org.android.go.sopt.domain.repository

import org.android.go.sopt.data.entity.remote.request.RequestPostLoginDto
import org.android.go.sopt.data.entity.remote.request.RequestPostSignUpDto
import org.android.go.sopt.data.entity.remote.response.ResponsePostLoginDto
import org.android.go.sopt.data.entity.remote.response.ResponsePostSignUpDto
import org.android.go.sopt.data.source.local.SharedPrefDataSource
import org.android.go.sopt.data.source.remote.AuthDataSource
import org.android.go.sopt.domain.model.User

class AuthRepository {
    private val authDataSource = AuthDataSource()
    private val sharedPrefDataSource = SharedPrefDataSource()

    /** 서버 통신 결과를 Result 타입으로 반환 */
    suspend fun postSignUp(requestPostSignUpDto: RequestPostSignUpDto): Result<ResponsePostSignUpDto?> =
        runCatching {
            authDataSource.postSignUp(requestPostSignUpDto).data
        }

    suspend fun postLogin(requestPostLoginDto: RequestPostLoginDto): Result<ResponsePostLoginDto?> =
        runCatching {
            authDataSource.postLogin(requestPostLoginDto).data
        }

    // 자동 로그인 체크
    fun isLastUserLoggedIn(): Boolean = sharedPrefDataSource.loginState

    // 로그인 상태 변화
    fun setLoginState(loginState: Boolean) {
        sharedPrefDataSource.loginState = loginState
    }

    fun getSignedUpUser(): User? = sharedPrefDataSource.signedUpUser

    fun setSignedUpUser(user: User) {
        sharedPrefDataSource.signedUpUser = user
    }
}