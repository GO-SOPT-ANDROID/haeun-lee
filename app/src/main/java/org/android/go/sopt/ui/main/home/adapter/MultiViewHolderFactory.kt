package org.android.go.sopt.ui.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import org.android.go.sopt.R
import org.android.go.sopt.ui.main.home.adapter.model.MultiViewItem
import org.android.go.sopt.ui.main.home.adapter.MultiViewHolder.*
import org.android.go.sopt.ui.main.home.adapter.MultiViewType.*

class MultiViewHolderFactory {
    fun getViewHolder(parent: ViewGroup, viewType: MultiViewType):
            MultiViewHolder<MultiViewItem> {
        return when (viewType) {
            HEADER_TYPE ->
                HeaderViewHolder(viewBind(parent, R.layout.item_header))
            REPO_TYPE ->
                RepoViewHolder(viewBind(parent, R.layout.item_repo))
        } as MultiViewHolder<MultiViewItem>
    }

    private fun <T : ViewDataBinding> viewBind(parent: ViewGroup, layoutRes: Int): T {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            layoutRes,
            parent,
            false
        )
    }
}