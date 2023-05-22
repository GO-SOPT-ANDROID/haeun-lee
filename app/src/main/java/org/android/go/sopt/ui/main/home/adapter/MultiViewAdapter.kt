package org.android.go.sopt.ui.main.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.android.go.sopt.domain.model.MultiViewItem
import org.android.go.sopt.domain.model.MultiViewItem.*
import org.android.go.sopt.ui.main.home.adapter.MultiViewHolder.*
import org.android.go.sopt.util.type.MultiViewType

class MultiViewAdapter(
    private val itemList: MutableList<MultiViewItem>
) : RecyclerView.Adapter<MultiViewHolder>() {
    private val multiViewHolderFactory by lazy { MultiViewHolderFactory() }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MultiViewHolder {
        return multiViewHolderFactory.getViewHolder(parent, MultiViewType.values()[viewType])
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: MultiViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> holder.bind(itemList[position] as Header)
            is RepoViewHolder -> holder.bind(itemList[position] as Repo)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return itemList[position].getViewType().ordinal
    }
}
