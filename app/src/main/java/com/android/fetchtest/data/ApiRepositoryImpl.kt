package com.android.fetchtest.data

import com.android.fetchtest.common.DataError
import com.android.fetchtest.common.Result
import com.android.fetchtest.domain.ApiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class ApiRepositoryImpl(private val apiService: ApiService) : ApiRepository {

    override suspend fun getItems(): Flow<Result<Collection<Item>, DataError>> = flow {
        emit(Result.Loading)
        try {
            emit(Result.Success(data = apiService.getItems()))
        }
        catch (e: retrofit2.HttpException) {
            when(e.code()) {
                400 -> emit(Result.Error(error = DataError.NetworkError.BAD_REQUEST))
                401 -> emit(Result.Error(error = DataError.NetworkError.Unauthorized))
                404 -> emit(Result.Error(error = DataError.NetworkError.NOT_FOUND))
                408 -> emit(Result.Error(error = DataError.NetworkError.REQUEST_TIMEOUT))
                413 -> emit(Result.Error(error = DataError.NetworkError.PAYLOAD_TOO_LARGE))
                429 -> emit(Result.Error(error = DataError.NetworkError.TOO_MANY_REQUESTS))
                503 -> emit(Result.Error(error = DataError.NetworkError.SERVICEUnAvailable))
                500 -> emit(Result.Error(error = DataError.NetworkError.SERVER_ERROR))
                else -> emit(Result.Error(error = DataError.NetworkError.UNKNOWN))
            }
        }
        catch (e: IOException) {
            emit(Result.Error(error = DataError.NetworkError.NO_INTERNET))
        }
        catch (ex: Exception) {
            emit(Result.Error(error = DataError.NetworkError.SOMETHING_WENT_WRONG_TRY_AGAIN_LATER))
        }
    }

}