package org.android.go.sopt.ui.main.home.adapter.model

import androidx.annotation.DrawableRes
import org.android.go.sopt.ui.main.home.adapter.MultiViewType
import org.android.go.sopt.ui.main.home.adapter.MultiViewType.*

sealed class MultiViewItem(
    private val viewType: MultiViewType
) {
    data class Header(val title: String) : MultiViewItem(HEADER_TYPE)

    data class Repo(
        @DrawableRes val imageRes: Int,
        val name: String,
        val author: String
    ) : MultiViewItem(REPO_TYPE)

    fun getViewType() = viewType
}