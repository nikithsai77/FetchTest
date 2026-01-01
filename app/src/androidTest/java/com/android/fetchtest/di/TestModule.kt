package com.android.fetchtest.di

import com.android.fetchtest.domain.useCase.ApiUseCase
import com.android.fetchtest.domain.util.DataError
import com.android.fetchtest.domain.model.FetchItem
import com.android.fetchtest.domain.Repository
import com.android.fetchtest.domain.util.Result
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Singleton
    @Provides
    fun apiFailure(@Named("failed")  repository: Repository): ApiUseCase {
        return ApiUseCase(repository)
    }

    @Singleton
    @Provides
    @Named("success")
    fun success(): Repository {
        return Success()
    }

    @Singleton
    @Provides
    @Named("failed")
    fun failed(): Repository {
        return Failed()
    }
}

private class Success : Repository {
    override suspend fun getItems(): Flow<Result<Collection<FetchItem>, DataError>> {
        return flowOf(
            Result.Loading, Result.Success(
                data = listOf(
                    FetchItem(listId = 1, name = "okay 1", id = 1),
                    FetchItem(listId = 1, name = "okay 1", id = 1),
                    FetchItem(listId = 2, name = "okay 2", id = 2),
                    FetchItem(listId = 1, name = "okay 1", id = 1),
                    FetchItem(listId = 2, name = "okay 2", id = 2),
                    FetchItem(listId = 1, name = "okay 1", id = 1),
                    FetchItem(listId = 3, name = "okay 3", id = 3),
                    FetchItem(listId = 3, name = "okay 3", id = 3)
                )
            )
        )
    }
}

private class Failed : Repository {
    override suspend fun getItems(): Flow<Result<Collection<FetchItem>, DataError>> {
        return flowOf(Result.Loading, Result.Error(error = DataError.NetworkError.NOT_FOUND))
    }
}
