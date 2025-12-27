package com.android.fetchtest.data.util

import com.android.fetchtest.data.model.ItemDTO
import com.android.fetchtest.domain.model.FetchItem

fun ItemDTO.toFetchItem(): FetchItem {
    return FetchItem(
        listId = listId,
        name = name,
        id = id
    )
}

fun FetchItem.toItemDTO(): ItemDTO {
    return ItemDTO(
        listId = listId,
        name = name,
        id = id
    )
}
