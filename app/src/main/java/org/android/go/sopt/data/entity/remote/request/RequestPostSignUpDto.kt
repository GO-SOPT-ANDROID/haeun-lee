package org.android.go.sopt.data.entity.remote.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.android.go.sopt.domain.model.User

@Serializable
data class RequestPostSignUpDto(
    @SerialName("id")
    val id: String,
    @SerialName("password")
    val password: String,
    @SerialName("name")
    val name: String,
    @SerialName("skill")
    val skill: String,
)