package com.android.fetchtest.data

import com.android.fetchtest.data.model.ItemDTO
import com.android.fetchtest.data.util.toFetchItem
import com.android.fetchtest.domain.util.DataError
import com.android.fetchtest.domain.util.Result
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import kotlin.test.assertEquals
import kotlin.test.fail

class ApiRepositoryImplTest {
    @MockK
    private lateinit var apiService: ApiService
    @InjectMockKs
    private lateinit var apiRepositoryImpl: ApiRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `test successful API call`() = runTest {
        val successfulOfItems = listOf(
            ItemDTO(listId = 1, name = "okay 1", id = 1),
            ItemDTO(listId = 3, name = "okay 3", id = 3),
            ItemDTO(listId = 2, name = "okay 2", id = 2)
        )
        coEvery { apiService.getItems() } returns successfulOfItems
        apiRepositoryImpl.getItems().collect {
            when(it) {
                is Result.Loading -> assertEquals(expected = Result.Loading, actual = it)
                is Result.Success -> assertEquals(
                    expected = successfulOfItems.map { itemDTO ->
                        itemDTO.toFetchItem()
                    }, actual = it.data
                )
                is Result.Error -> fail(message = "Unexpected result type $it")
            }
        }
    }

    @Test
    fun `test fails API call`() = runTest {
        coEvery { apiService.getItems() } throws HttpException(Response.error<HttpException>(404, "".toResponseBody(contentType = null)))
        apiRepositoryImpl.getItems().collect {
            when(it) {
                is Result.Loading -> assertEquals(expected = Result.Loading, actual = it)
                is Result.Success -> fail(message = "Unexpected result type $it")
                is Result.Error -> assertEquals(expected = DataError.NetworkError.NOT_FOUND, actual = it.error)
            }
        }
    }

    @Test
    fun `test fails API call when other exception raise`() = runTest {
        coEvery { apiService.getItems() } throws RuntimeException()
        apiRepositoryImpl.getItems().collect {
            when(it) {
                is Result.Loading -> assertEquals(expected =  Result.Loading, actual = it)
                is Result.Success -> fail(message = "Unexpected result type $it")
                is Result.Error -> assertEquals(
                    expected = DataError.NetworkError.SOMETHING_WENT_WRONG_TRY_AGAIN_LATER,
                    actual = it.error
                )
            }
        }
    }

}
