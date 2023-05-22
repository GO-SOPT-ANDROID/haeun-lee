package org.android.go.sopt.data.service

import org.android.go.sopt.data.entity.remote.response.ResponseGetFollowerListDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FollowerService {
    @GET("users")
    suspend fun getFollowerList(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ) : ResponseGetFollowerListDto
}