package org.android.go.sopt.data.model.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.android.go.sopt.data.entity.Follower

@Serializable
data class ResponseGetFollowerListDto(
    val page: Int,
    @SerialName("per_page")
    val perPage: Int,
    val total: Int,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("data")
    val data: List<FollowerDto>,
    val support: Support
) {
    @Serializable
    data class FollowerDto(
        val id: Int,
        val email: String,
        @SerialName("first_name")
        val firstName: String,
        @SerialName("last_name")
        val lastName: String,
        @SerialName("avatar")
        val avatar: String
    )

    @Serializable
    data class Support(
        val url: String,
        val text: String
    )

    fun toFollowerList() = data.map { followerDto ->
        Follower(
            id = followerDto.id,
            name = followerDto.firstName,
            email = followerDto.email,
            imgUrl = followerDto.avatar
        )
    }
}