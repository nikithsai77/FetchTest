package com.android.fetchtest.data

import com.android.fetchtest.common.DataError
import com.android.fetchtest.common.Result
import com.android.fetchtest.domain.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.io.IOException

class ApiRepositoryImpl(private val apiService: ApiService) : Repository {

    override suspend fun getItems(): Flow<Result<Collection<Item>, DataError.NetworkError>> = flow {
        emit(Result.Loading)
        emit(Result.Success(data = apiService.getItems()) as Result<Collection<Item>, DataError.NetworkError>)
    }.catch { e ->
        when(e) {
            is retrofit2.HttpException -> {
                when(e.code()) {
                    400 -> emit(Result.Error(error = DataError.NetworkError.BAD_REQUEST))
                    401 -> emit(Result.Error(error = DataError.NetworkError.Unauthorized))
                    403 -> emit(Result.Error(error = DataError.NetworkError.Forbidden))
                    404 -> emit(Result.Error(error = DataError.NetworkError.NOT_FOUND))
                    405 -> emit(Result.Error(error = DataError.NetworkError.REQUEST_NOT_ALLOWED))
                    408 -> emit(Result.Error(error = DataError.NetworkError.REQUEST_TIMEOUT))
                    409 -> emit(Result.Error(error = DataError.NetworkError.Conflict))
                    413 -> emit(Result.Error(error = DataError.NetworkError.PAYLOAD_TOO_LARGE))
                    415 -> emit(Result.Error(error = DataError.NetworkError.UNSUPPORTED_MEDIA_TYPE))
                    429 -> emit(Result.Error(error = DataError.NetworkError.TOO_MANY_REQUESTS))
                    500 -> emit(Result.Error(error = DataError.NetworkError.SERVER_ERROR))
                    503 -> emit(Result.Error(error = DataError.NetworkError.SERVICEUnAvailable))
                    else -> emit(Result.Error(error = DataError.NetworkError.SOMETHING_WENT_WRONG_TRY_AGAIN_LATER))
              }
            }
            is IOException -> emit(Result.Error(error = DataError.NetworkError.NO_INTERNET))
            else -> emit(Result.Error(error = DataError.NetworkError.SOMETHING_WENT_WRONG_TRY_AGAIN_LATER))
        }
    }

}