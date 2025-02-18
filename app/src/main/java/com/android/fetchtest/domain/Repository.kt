package com.android.fetchtest.domain

import com.android.fetchtest.data.Item
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getItems(): Flow<Result<Collection<Item>, DataError>>
}