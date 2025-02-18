package com.android.fetchtest.data

import com.android.fetchtest.domain.DataError
import com.android.fetchtest.domain.Repository
import com.android.fetchtest.domain.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ApiRepositoryImplSuccess : Repository {
    override suspend fun getItems(): Flow<Result<Collection<Item>, DataError>> {
        return flowOf(Result.Loading, Result.Success(data=listOf(
            Item(listId = 1, name = "okay 1", id = 1),
            Item(listId = 1, name = "okay 1", id = 2),
            Item(listId = 2, name = "okay 2", id = 1),
            Item(listId = 1, name = "okay 1", id = 3),
            Item(listId = 2, name = "okay 2", id = 2),
            Item(listId = 1, name = "okay 1", id = 4),
            Item(listId = 3, name = "okay 3", id = 1),
            Item(listId = 3, name = "okay 3", id = 2))))
    }
}