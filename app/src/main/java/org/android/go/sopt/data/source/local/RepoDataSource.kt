package org.android.go.sopt.data.source.local

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.android.go.sopt.data.entity.local.MockRepoDto
import org.android.go.sopt.util.AssetLoader

class RepoDataSource(private val assetLoader: AssetLoader) {
    fun decodeJsonToDtoList(): List<MockRepoDto> {
        return assetLoader.getJsonString(FILE_MOCK_REPO_LIST)?.let { jsonString ->
            Json.decodeFromString<List<MockRepoDto>>(jsonString)
        } ?: emptyList()
    }

    companion object {
        private const val FILE_MOCK_REPO_LIST = "mock_repo_list.json"
    }
}