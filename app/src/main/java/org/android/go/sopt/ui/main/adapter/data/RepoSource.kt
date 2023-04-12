package org.android.go.sopt.ui.main.adapter.data

import org.android.go.sopt.R
import org.android.go.sopt.ui.main.adapter.model.Repo

class RepoSource {
    fun loadRepoSources(): List<Repo> {
        return listOf(
            Repo(R.drawable.ic_launcher_background, "name1", "author1"),
            Repo(R.drawable.ic_launcher_background, "name2", "author2"),
            Repo(R.drawable.ic_launcher_background, "name3", "author3"),
            Repo(R.drawable.ic_launcher_background, "name4", "author4"),
            Repo(R.drawable.ic_launcher_background, "name5", "author5"),
        )
    }
}