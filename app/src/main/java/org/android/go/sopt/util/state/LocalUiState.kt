package org.android.go.sopt.util.state

// sealed 클래스는 자식 클래스의 종류를 제한할 수 있다.
sealed class LocalUiState {
    object Loading : LocalUiState()
    object Success : LocalUiState()
    data class Failure(val code: Int?) : LocalUiState()
}