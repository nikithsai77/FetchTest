package com.android.fetchtest.domain

import com.android.fetchtest.domain.model.FetchItem
import com.android.fetchtest.domain.util.DataError
import com.android.fetchtest.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getItems(): Flow<Result<Collection<FetchItem>, DataError>>
}
