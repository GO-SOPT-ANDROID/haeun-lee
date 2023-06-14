package org.android.go.sopt.presentation.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.serialization.json.Json
import org.android.go.sopt.GoSoptApplication
import org.android.go.sopt.data.model.local.MockRepoDto
import org.android.go.sopt.data.entity.MultiViewItem.*
import org.android.go.sopt.util.state.LocalUiState
import org.android.go.sopt.util.state.LocalUiState.*
import timber.log.Timber

class HomeViewModel : ViewModel() {
    private val _repoList = MutableLiveData<List<Repo>>()
    val repoList: List<Repo>?
        get() = _repoList.value

    private val _getRepoListState = MutableLiveData<LocalUiState>()
    val getRepoListState: LiveData<LocalUiState>
        get() = _getRepoListState

    init {
        getRepoList()
    }

    private fun getRepoList() {
        _getRepoListState.value = Loading

        getRepoListResult()
            .onSuccess { response ->
                if (response.isEmpty()) {
                    _getRepoListState.value = Failure(null)
                    Timber.e("GET REPO LIST FAIL : EMPTY LIST")
                    return@onSuccess
                }

                _repoList.value = response
                _getRepoListState.value = Success
                Timber.d("GET REPO LIST SUCCESS : $response")
            }
            .onFailure { t ->
                _getRepoListState.value = Failure(null)
                Timber.e("GET REPO LIST FAIL : ${t.message}")
            }
    }

    private fun getRepoListResult(): Result<List<Repo>> = runCatching {
        decodeJsonToDtoList().map { mockRepoDto -> mockRepoDto.toRepo() }
    }

    private fun decodeJsonToDtoList(): List<MockRepoDto> {
        return GoSoptApplication.mockJsonString?.let { json ->
            Json.decodeFromString<List<MockRepoDto>>(json)
        } ?: emptyList()
    }
}