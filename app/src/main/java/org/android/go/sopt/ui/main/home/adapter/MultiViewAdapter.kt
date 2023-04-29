package org.android.go.sopt.ui.main.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.android.go.sopt.ui.main.home.adapter.model.MultiViewItem

class MultiViewAdapter(
    private val itemList: ArrayList<MultiViewItem>
) : RecyclerView.Adapter<MultiViewHolder<MultiViewItem>>() {
    private val multiViewHolderFactory by lazy { MultiViewHolderFactory() }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MultiViewHolder<MultiViewItem> {
        return multiViewHolderFactory.getViewHolder(parent, MultiViewType.values()[viewType])
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: MultiViewHolder<MultiViewItem>, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemViewType(position: Int): Int {
        return itemList[position].getViewType().ordinal
    }
}
