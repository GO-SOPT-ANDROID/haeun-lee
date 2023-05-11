package org.android.go.sopt.data.local.source

import android.content.Context
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.android.go.sopt.data.local.model.MockRepoDto
import org.android.go.sopt.domain.model.MultiViewItem.*

class RepoDataSource(private val context: Context) {
    fun getRepoList(): Result<List<Repo>> = runCatching {
        decodeJsonToDtoList().map { mockRepoDto -> mockRepoDto.toRepo() }
    }

    // jsonString -> List<MockRepoDto>
    private fun decodeJsonToDtoList(): List<MockRepoDto> {
        return getJsonString(FILE_MOCK_REPO_LIST)?.let { jsonString ->
            Json.decodeFromString<List<MockRepoDto>>(jsonString)
        } ?: emptyList()
    }

    private fun getJsonString(fileName: String): String? {
        return kotlin.runCatching {
            loadAsset(fileName)
        }.getOrNull()
    }

    private fun loadAsset(fileName: String): String {
        return context.assets.open(fileName).use { inputStream ->
            val size = inputStream.available()
            val bytes = ByteArray(size)
            inputStream.read(bytes)
            String(bytes)
        }
    }

    companion object {
        private const val FILE_MOCK_REPO_LIST = "mock_repo_list.json"
    }
}