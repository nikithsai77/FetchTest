package com.android.fetchtest.common

sealed class Resource<out T>(val data: T? = null, val errorMessage: String? = null) {
    object Loading : Resource<Nothing>()
    class Success<T>(itemList: T) : Resource<T>(data = itemList)
    class Error<T>(error: String) : Resource<T>(errorMessage = error)
}
