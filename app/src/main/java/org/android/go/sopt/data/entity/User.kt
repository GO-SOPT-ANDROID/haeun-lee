package org.android.go.sopt.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: String,
    val pw: String,
    val name: String,
    val hobby: String
): Parcelable
