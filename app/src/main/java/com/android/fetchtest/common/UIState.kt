package com.android.fetchtest.common

import com.android.fetchtest.data.Item

sealed class UIState {
    object Loading : UIState()
    data class Error(val error: String) : UIState()
    data class Success(val itemList: Map<Int, List<Item>>) : UIState()
}
