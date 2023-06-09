package org.android.go.sopt.presentation.main.home.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import org.android.go.sopt.databinding.ItemHeaderBinding
import org.android.go.sopt.databinding.ItemRepoBinding
import org.android.go.sopt.data.entity.MultiViewItem.*

/** sealed class는 추상 클래스로 자식 클래스의 종류를 제한할 수 있다. */
sealed class MultiViewHolder(
    binding: ViewDataBinding
) : RecyclerView.ViewHolder(binding.root) {

    class HeaderViewHolder(
        private val binding: ItemHeaderBinding
    ) : MultiViewHolder(binding) {
        fun bind(item: Header) {
            binding.title = item.title
        }
    }

    class RepoViewHolder(
        private val binding: ItemRepoBinding
    ) : MultiViewHolder(binding) {
        fun bind(item: Repo) {
            binding.repo = item
        }
    }
}