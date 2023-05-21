package org.android.go.sopt.data.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.android.go.sopt.BuildConfig
import org.android.go.sopt.data.remote.service.AuthService
import retrofit2.Retrofit

object AuthFactory {
    private val okHttpClient = OkHttpClient.Builder().addInterceptor(
        HttpLoggingInterceptor().apply {
            // 로그에 포함되는 정보의 수준
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                    else HttpLoggingInterceptor.Level.NONE
        }).build()

    @OptIn(ExperimentalSerializationApi::class)
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.AUTH_BASE_URL)
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .client(okHttpClient)
        .build()

    inline fun <reified T> create(): T = retrofit.create(T::class.java)

    object ServicePool {
        val authService = create<AuthService>()
    }
}