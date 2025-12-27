package com.android.fetchtest.domain

import com.android.fetchtest.data.ApiRepositoryImplFailure
import com.android.fetchtest.data.ApiRepositoryImplSuccess
import com.android.fetchtest.domain.model.FetchItem
import com.android.fetchtest.domain.useCase.ApiUseCase
import com.android.fetchtest.domain.util.DataError
import com.android.fetchtest.domain.util.Result
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class ApiUseCaseTest {

    @Test
    fun successCase() = runTest{
        val success = mapOf(
            1 to listOf(
                FetchItem(listId = 1, name = "okay 1", id = 1),
                FetchItem(listId = 1, name = "okay 1", id = 2),
                FetchItem(listId = 1, name = "okay 1", id = 3),
                FetchItem(listId = 1, name = "okay 1", id = 4)
            ),
            2 to listOf(
                FetchItem(listId = 2, name = "okay 2", id = 1),
                FetchItem(listId = 2, name = "okay 2", id = 2)
            ),
            3 to listOf(
                FetchItem(listId = 3, name = "okay 3", id = 1),
                FetchItem(listId = 3, name = "okay 3", id = 2)
            )
        )
        val apiUseCase = ApiUseCase(repository = ApiRepositoryImplSuccess())
        apiUseCase().collect {
            when(it) {
                is Result.Loading -> assertEquals(expected = Result.Loading, actual = it)
                is Result.Success-> assertEquals(expected =  success, actual = it.data)
                is Result.Error-> fail(message = "Unexpected result type $it")
            }
        }
    }

    @Test
    fun failureCase() = runTest {
        val apiUseCase = ApiUseCase(repository = ApiRepositoryImplFailure())
        apiUseCase().collect {
            when(it) {
                is Result.Loading -> assertEquals(expected = Result.Loading, actual = it)
                is Result.Success-> fail(message = "Unexpected result type $it")
                is Result.Error-> assertEquals(expected = DataError.NetworkError.NOT_FOUND, actual = it.error)
            }
        }
    }

}
