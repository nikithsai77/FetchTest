package com.android.fetchtest.domain

import com.android.fetchtest.data.ApiRepositoryImplFailure
import com.android.fetchtest.data.ApiRepositoryImplSuccess
import com.android.fetchtest.data.Item
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class ApiUseCaseTest {

    @Test
    fun successCase() = runTest{
        val success = mapOf(
            1 to listOf(Item(listId = 1, name = "okay 1", id = 1), Item(listId = 1, name = "okay 1", id = 2), Item(listId = 1, name = "okay 1", id = 3), Item(listId = 1, name = "okay 1", id = 4)),
            2 to listOf(Item(listId = 2, name = "okay 2", id = 1), Item(listId = 2, name = "okay 2", id = 2)),
            3 to listOf(Item(listId = 3, name = "okay 3", id = 1), Item(listId = 3, name = "okay 3", id = 2))
        )
        val apiUseCase = ApiUseCase(ApiRepositoryImplSuccess())
        apiUseCase().collect {
            when(it) {
                is Result.Loading -> assertEquals(Result.Loading, it)
                is Result.Success-> assertEquals(success, it.data)
                is Result.Error-> fail("Unexpected result type $it")
            }
        }
    }

    @Test
    fun failureCase() = runTest {
        val apiUseCase = ApiUseCase(ApiRepositoryImplFailure())
        apiUseCase().collect {
            when(it) {
                is Result.Loading -> assertEquals(Result.Loading, it)
                is Result.Success-> fail("Unexpected result type $it")
                is Result.Error-> assertEquals(DataError.NetworkError.NOT_FOUND, it.error)
            }
        }
    }

}