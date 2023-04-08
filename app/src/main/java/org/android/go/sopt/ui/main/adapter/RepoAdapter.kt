package org.android.go.sopt.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.android.go.sopt.databinding.ItemGithubRepoBinding
import org.android.go.sopt.ui.main.adapter.model.Repo

class RepoAdapter(
    private val itemList: List<Repo>
) : RecyclerView.Adapter<RepoAdapter.RepoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val binding = ItemGithubRepoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return RepoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size

    class RepoViewHolder(
        private val binding: ItemGithubRepoBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Repo) {
            binding.tvName.text = item.name
            binding.tvAuthor.text = item.author
        }
    }
}