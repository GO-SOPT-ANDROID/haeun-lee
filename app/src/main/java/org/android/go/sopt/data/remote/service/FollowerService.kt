package org.android.go.sopt.data.remote.service

import org.android.go.sopt.data.remote.entity.response.ResponseGetFollowerListDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FollowerService {
    @GET("users")
    fun getFollowerList(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ) : Call<ResponseGetFollowerListDto>
}