package org.android.go.sopt.data.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.android.go.sopt.BuildConfig
import org.android.go.sopt.data.remote.service.SignUpService
import retrofit2.Retrofit
import timber.log.Timber

object ApiFactory {
    // ApiFactory가 싱글톤으로 생성될 때
    // retrofit 객체도 한번만 초기화 되도록 by lazy 대신 일반적인 프로퍼티로 선언
    @OptIn(ExperimentalSerializationApi::class)
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.AUTH_BASE_URL)
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .build()

    inline fun <reified T> create(): T = retrofit.create(T::class.java)

    object ServicePool {
        val signUpService = create<SignUpService>()
    }
}