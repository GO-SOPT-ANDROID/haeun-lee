package org.android.go.sopt.ui.main.gallery.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import org.android.go.sopt.databinding.ItemRepoBinding
import org.android.go.sopt.ui.main.gallery.adapter.model.Repo
import java.util.*

class MyListAdapter : ListAdapter<Repo, MyViewHolder>(diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemRepoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun moveItem(fromPosition: Int, toPosition: Int) {
        val newList = currentList.toMutableList()
        Collections.swap(newList, fromPosition, toPosition)
        submitList(newList)
    }

    fun removeItem(position: Int) {
        val newList = currentList.toMutableList()
        newList.removeAt(position)
        submitList(newList)
    }

    companion object {
        private val diffUtil = MyDiffCallback<Repo>(
            onItemsTheSame = { old, new -> old === new },
            onContentsTheSame = { old, new -> old == new }
        )
    }
}