package com.android.fetchtest.domain

import com.android.fetchtest.common.DataError
import com.android.fetchtest.common.Result
import com.android.fetchtest.data.Item
import kotlinx.coroutines.flow.Flow

interface ApiRepository {
    suspend fun getItems(): Flow<Result<Collection<Item>, DataError>>
}