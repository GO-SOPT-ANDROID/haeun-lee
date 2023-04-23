package org.android.go.sopt.ui.main.home.adapter

import androidx.annotation.DrawableRes

sealed class MultiViewItem(
    private val viewType: MultiViewType
) {
    data class Header(val title: String) : MultiViewItem(MultiViewType.TEXT)

    data class Repo(
        @DrawableRes val imageRes: Int,
        val name: String,
        val author: String
    ) : MultiViewItem(MultiViewType.IMAGE)

    fun getViewType() = viewType
}