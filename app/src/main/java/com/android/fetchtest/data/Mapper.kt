package com.android.fetchtest.data

import com.android.fetchtest.domain.FetchItem

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
