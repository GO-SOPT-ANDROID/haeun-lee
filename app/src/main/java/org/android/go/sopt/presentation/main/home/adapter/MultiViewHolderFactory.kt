package org.android.go.sopt.presentation.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import org.android.go.sopt.R
import org.android.go.sopt.presentation.main.home.adapter.MultiViewHolder.*
import org.android.go.sopt.util.type.MultiViewType
import org.android.go.sopt.util.type.MultiViewType.*

class MultiViewHolderFactory {
    fun getViewHolder(parent: ViewGroup, viewType: MultiViewType): MultiViewHolder {
        return when (viewType) {
            HEADER ->
                HeaderViewHolder(viewBind(parent, R.layout.item_header))
            REPO ->
                RepoViewHolder(viewBind(parent, R.layout.item_repo))
        }
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