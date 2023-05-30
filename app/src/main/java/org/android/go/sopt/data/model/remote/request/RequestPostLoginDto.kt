package org.android.go.sopt.data.model.remote.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestPostLoginDto(
    @SerialName("id")
    val id: String,
    @SerialName("password")
    val password: String,
)