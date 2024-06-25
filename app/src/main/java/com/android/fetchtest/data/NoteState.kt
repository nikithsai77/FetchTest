package com.android.fetchtest.data

data class NoteState(
    val itemList: Map<Int, List<Item>> = emptyMap(),
    val isLoading: Boolean = false,
    val isFailed: Boolean = false,
    val errorMsg: String = ""
)
