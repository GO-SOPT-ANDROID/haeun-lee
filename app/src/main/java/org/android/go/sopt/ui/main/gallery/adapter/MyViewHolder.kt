package org.android.go.sopt.ui.main.gallery.adapter

import androidx.recyclerview.widget.RecyclerView
import org.android.go.sopt.databinding.ItemImageBinding
import org.android.go.sopt.ui.main.gallery.Repo

class MyViewHolder(private val binding: ItemImageBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Repo) {
        with(binding){
            ivImage.setImageResource(item.imageRes)
            name = item.name
            author = item.author
        }
    }
}