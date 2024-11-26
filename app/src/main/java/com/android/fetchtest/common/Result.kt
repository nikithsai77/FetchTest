package com.android.fetchtest.common

sealed interface Result<out D, out E: DataError> {
    object Loading: Result<Nothing, Nothing>
    data class Success<out D, out E: DataError>(val data: D): Result<D, E>
    data class Error<out D, out E: DataError>(val error: E): Result<D, E>
}