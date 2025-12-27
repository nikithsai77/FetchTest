package com.android.fetchtest.domain.util

sealed interface Result<out D, out E: DataError> {
    data object Loading: Result<Nothing, Nothing>
    data class Success<out D>(val data: D): Result<D, Nothing>
    data class Error<out E: DataError>(val error: E): Result<Nothing, E>
}
