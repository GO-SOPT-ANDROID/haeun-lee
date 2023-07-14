package org.android.go.sopt.data.service

import okhttp3.MultipartBody
import org.android.go.sopt.data.model.remote.response.base.BaseResponse
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ImageService {
    @Multipart
    @POST("upload")
    suspend fun postImage(
        @Part file: MultipartBody.Part
    ) : BaseResponse<Unit>
}