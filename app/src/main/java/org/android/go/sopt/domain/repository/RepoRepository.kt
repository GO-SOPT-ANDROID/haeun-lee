package org.android.go.sopt.domain.repository

import android.content.Context
import org.android.go.sopt.data.source.local.RepoDataSource
import org.android.go.sopt.domain.model.MultiViewItem.*
import org.android.go.sopt.util.AssetLoader

class RepoRepository(context: Context) {
    private val assetLoader = AssetLoader(context)
    private val repoDataSource = RepoDataSource(assetLoader)

    fun getRepoList(): Result<List<Repo>> = runCatching {
        repoDataSource.decodeJsonToDtoList().map { mockRepoDto ->
            mockRepoDto.toRepo()
        }
    }
}