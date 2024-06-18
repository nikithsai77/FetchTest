package com.android.fetchtest.data

data class NoteState(
    val itemList: List<Item> = emptyList(),
    val isLoading: Boolean = false,
    val isFailed: Boolean = false,
    val errorMsg: String = ""
)
