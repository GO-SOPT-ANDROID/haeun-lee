package org.android.go.sopt.ui.main.gallery.adapter

import androidx.recyclerview.widget.DiffUtil
import org.android.go.sopt.ui.main.gallery.adapter.model.Repo

class MyDiffCallback<T : Any>(
    val onItemsTheSame: (T, T) -> Boolean,
    val onContentsTheSame: (T, T) -> Boolean
) : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
        onItemsTheSame(oldItem, newItem)

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
        onContentsTheSame(oldItem, newItem)
}