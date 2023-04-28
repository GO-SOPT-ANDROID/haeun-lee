package org.android.go.sopt.ui.main.home.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import org.android.go.sopt.databinding.ItemHeaderBinding
import org.android.go.sopt.databinding.ItemRepoBinding
import org.android.go.sopt.ui.main.home.adapter.model.MultiViewItem

/** sealed class는 추상 클래스로 자식 클래스의 종류를 제한할 수 있다. */
sealed class MultiViewHolder<E : MultiViewItem>(
    binding: ViewDataBinding
) : RecyclerView.ViewHolder(binding.root) {

    class HeaderViewHolder(
        private val binding: ItemHeaderBinding
    ) : MultiViewHolder<MultiViewItem.Header>(binding) {
        override fun bind(item: MultiViewItem.Header) {
            binding.title = item.title
        }
    }

    class RepoViewHolder(
        private val binding: ItemRepoBinding
    ) : MultiViewHolder<MultiViewItem.Repo>(binding) {
        override fun bind(item: MultiViewItem.Repo) {
            with(binding){
                ivImage.setImageResource(item.imageRes)
                name = item.name
                author = item.author
            }
        }
    }

    abstract fun bind(item: E)
}