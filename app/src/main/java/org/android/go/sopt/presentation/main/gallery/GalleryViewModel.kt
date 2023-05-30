package org.android.go.sopt.presentation.main.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.android.go.sopt.data.ReqResFactory
import org.android.go.sopt.data.entity.Follower
import org.android.go.sopt.util.state.RemoteUiState
import org.android.go.sopt.util.state.RemoteUiState.*
import retrofit2.HttpException
import timber.log.Timber

class GalleryViewModel : ViewModel() {
    private val _followerList = MutableLiveData<List<Follower>>()
    val followerList: List<Follower>?
        get() = _followerList.value

    private val _getFollowerListState = MutableLiveData<RemoteUiState>()
    val getFollowerListState: LiveData<RemoteUiState>
        get() = _getFollowerListState

    init {
        getFollowerList()
    }

    private fun getFollowerList() {
        viewModelScope.launch {
            getFollowerListResult(PAGE, PER_PAGE)
                .onSuccess { response ->
                    if (response.isEmpty()) {
                        _getFollowerListState.value = Failure(null)
                        return@onSuccess
                    }

                    _followerList.value = response
                    _getFollowerListState.value = Success
                    Timber.d("GET FOLLOWER LIST SUCCESS : $response")
                }
                .onFailure { t ->
                    if (t is HttpException) {
                        _getFollowerListState.value = Error
                        Timber.e("GET FOLLOWER LIST FAIL : ${t.message}")
                    }
                }
        }
    }

    private suspend fun getFollowerListResult(page: Int, perPage: Int): Result<List<Follower>> =
        runCatching {
            ReqResFactory.ServicePool.followerService.getFollowerList(page, perPage)
                .toFollowerList()
        }

    companion object {
        private const val PAGE = 1
        private const val PER_PAGE = 10
    }
}