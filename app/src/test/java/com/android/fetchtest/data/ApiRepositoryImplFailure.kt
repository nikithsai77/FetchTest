package com.android.fetchtest.data

import com.android.fetchtest.domain.util.DataError
import com.android.fetchtest.domain.model.FetchItem
import com.android.fetchtest.domain.Repository
import com.android.fetchtest.domain.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ApiRepositoryImplFailure : Repository {
    override suspend fun getItems(): Flow<Result<Collection<FetchItem>, DataError>> {
        return flowOf(Result.Loading, Result.Error(error = DataError.NetworkError.NOT_FOUND))
    }
}
