package org.android.go.sopt.ui.main.adapter.model

import androidx.annotation.DrawableRes

data class Repo(
    @DrawableRes val image: Int,
    val name: String,
    val author: String
)