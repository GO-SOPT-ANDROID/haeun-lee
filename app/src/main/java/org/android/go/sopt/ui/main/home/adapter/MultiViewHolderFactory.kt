package org.android.go.sopt.ui.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import org.android.go.sopt.R

class MultiViewHolderFactory {
    @Suppress("UNCHECKED_CAST")
    fun getViewHolder(parent: ViewGroup, viewType: MultiViewType): MultiViewHolder<MultiViewItem> {
        return when (viewType) {
            MultiViewType.TEXT ->
                MultiViewHolder.TextViewHolder(viewBind(parent, R.layout.item_text))
            MultiViewType.IMAGE ->
                MultiViewHolder.ImageViewHolder(viewBind(parent, R.layout.item_image))
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