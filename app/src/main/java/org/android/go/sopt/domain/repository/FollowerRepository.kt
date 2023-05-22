package org.android.go.sopt.domain.repository

import org.android.go.sopt.data.source.remote.FollowerDataSource
import org.android.go.sopt.domain.model.Follower

class FollowerRepository {
    suspend fun getFollowerList(page: Int, perPage: Int): Result<List<Follower>> =
        runCatching {
            FollowerDataSource().getFollowerList(page, perPage).toFollowerList()
        }
}