package org.android.go.sopt.ui.main.gallery.adapter

import androidx.recyclerview.widget.DiffUtil
import org.android.go.sopt.ui.main.gallery.Repo

class MyDiffCallback : DiffUtil.ItemCallback<Repo>() {
    override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean {
        return oldItem == newItem
    }
}