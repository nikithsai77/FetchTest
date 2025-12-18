package com.android.fetchtest.domain

import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getItems(): Flow<Result<Collection<FetchItem>, DataError>>
}
