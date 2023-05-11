package org.android.go.sopt.domain.model

import org.android.go.sopt.ui.main.home.adapter.MultiViewType
import org.android.go.sopt.ui.main.home.adapter.MultiViewType.*

sealed class MultiViewItem(
    private val viewType: MultiViewType
) {
    data class Header(val title: String) : MultiViewItem(HEADER_TYPE)

    data class Repo(
        val id: Int,
        val imgUrl: String,
        val name: String,
        val owner: String
    ) : MultiViewItem(REPO_TYPE)

    fun getViewType() = viewType
}