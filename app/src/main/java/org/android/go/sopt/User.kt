package org.android.go.sopt

import java.io.Serializable

data class User(
    val id: String,
    val pw: String,
    val name: String,
    val hobby: String
): Serializable
