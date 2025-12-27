package com.android.fetchtest.data

import com.android.fetchtest.domain.util.DataError
import com.android.fetchtest.domain.model.FetchItem
import com.android.fetchtest.domain.Repository
import com.android.fetchtest.domain.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ApiRepositoryImplSuccess : Repository {
    override suspend fun getItems(): Flow<Result<Collection<FetchItem>, DataError>> {
        return flowOf(Result.Loading, Result.Success(data=listOf(
            FetchItem(listId = 1, name = "okay 1", id = 1),
            FetchItem(listId = 1, name = "okay 1", id = 2),
            FetchItem(listId = 2, name = "okay 2", id = 1),
            FetchItem(listId = 1, name = "okay 1", id = 3),
            FetchItem(listId = 2, name = "okay 2", id = 2),
            FetchItem(listId = 1, name = "okay 1", id = 4),
            FetchItem(listId = 3, name = "okay 3", id = 1),
            FetchItem(listId = 3, name = "okay 3", id = 2))))
    }
}