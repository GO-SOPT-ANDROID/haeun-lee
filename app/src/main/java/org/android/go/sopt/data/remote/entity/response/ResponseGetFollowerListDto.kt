package org.android.go.sopt.data.remote.entity.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseGetFollowerListDto(
    val page: Int,
    @SerialName("per_page")
    val perPage: Int,
    val total: Int,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("data")
    val data: List<Follower>,
    val support: Support
) {
    @Serializable
    data class Follower(
        val id: Int,
        val email: String,
        @SerialName("first_name")
        val firstName: String,
        @SerialName("last_name")
        val lastName: String,
        @SerialName("avatar")
        val imgUrl: String
    )

    @Serializable
    data class Support(
        val url: String,
        val text: String
    )
}