package com.android.fetchtest.common

import com.android.fetchtest.domain.Item

sealed interface UIState {
    object Loading : UIState
    data class Success(val data: Map<Int, List<Item>>): UIState
    data class Error(val error: String): UIState
}