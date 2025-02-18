package com.android.fetchtest.data

import com.android.fetchtest.domain.DataError
import com.android.fetchtest.domain.Repository
import com.android.fetchtest.domain.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ApiRepositoryImplFailure : Repository {
    override suspend fun getItems(): Flow<Result<Collection<Item>, DataError>> {
        return flowOf(Result.Loading, Result.Error(error = DataError.NetworkError.NOT_FOUND))
    }
}