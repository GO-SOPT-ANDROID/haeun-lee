package org.android.go.sopt.data.remote.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class RequestSignUpDto(
    @SerialName("id")
    val id: String,
    @SerialName("password")
    val password: String,
    @SerialName("name")
    val name: String,
    @SerialName("skill")
    val skill: String,
)

@Serializable
data class ResponseSignUpDto(
    @SerialName("status")
    val status: Int,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: SignUpData,
) {
    @Serializable
    data class SignUpData(
        @SerialName("name")
        val name: String,
        @SerialName("skill")
        val skill: String,
    )
}