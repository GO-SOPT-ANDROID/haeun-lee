package org.android.go.sopt.data.source.remote

import org.android.go.sopt.data.entity.remote.response.ResponseGetFollowerListDto
import org.android.go.sopt.data.module.ReqResFactory

class FollowerDataSource {
    suspend fun getFollowerList(page: Int, perPage: Int): ResponseGetFollowerListDto {
        val followerService = ReqResFactory.ServicePool.followerService
        return followerService.getFollowerList(page, perPage)
    }
}