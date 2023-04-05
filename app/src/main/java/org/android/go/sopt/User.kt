package org.android.go.sopt

import java.io.Serializable

data class User(
    var id: String,
    var pw: String,
    var name: String,
    var hobby: String
): Serializable
