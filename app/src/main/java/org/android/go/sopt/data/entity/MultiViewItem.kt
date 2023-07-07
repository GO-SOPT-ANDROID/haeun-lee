package org.android.go.sopt.data.entity

import org.android.go.sopt.presentation.main.home.adapter.MultiViewType
import org.android.go.sopt.presentation.main.home.adapter.MultiViewType.*

sealed class MultiViewItem(
    private val viewType: MultiViewType
) {
    data class Header(val title: String) : MultiViewItem(HEADER)

    data class Repo(
        val id: Int,
        val imgUrl: String,
        val name: String,
        val owner: String
    ) : MultiViewItem(REPO)

    fun getViewType() = viewType
}