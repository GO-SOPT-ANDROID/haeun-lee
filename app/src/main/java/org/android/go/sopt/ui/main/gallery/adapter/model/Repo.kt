package org.android.go.sopt.ui.main.gallery.adapter.model

import androidx.annotation.DrawableRes

data class Repo(
    @DrawableRes val imageRes: Int,
    val name: String,
    val author: String
)
