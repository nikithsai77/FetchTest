package com.android.fetchtest.data

import com.android.fetchtest.domain.DataError
import com.android.fetchtest.domain.Result
import kotlin.test.assertEquals
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.HttpException
import retrofit2.Response
import kotlin.test.fail

class ApiRepositoryImplTest {
    @Mock
    private lateinit var apiService: ApiService
    @InjectMocks
    private lateinit var apiRepositoryImpl: ApiRepositoryImpl

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `test successful API call`() = runTest {
        val successfulOfItems = listOf(
            ItemDTO(listId = 1, name = "okay 1", id = 1),
            ItemDTO(listId = 3, name = "okay 3", id = 3),
            ItemDTO(listId = 2, name = "okay 2", id = 2)
        )
        Mockito.`when`(apiService.getItems()).thenReturn(successfulOfItems)
        apiRepositoryImpl.getItems().collect {
            when(it) {
                is Result.Loading -> assertEquals(expected = Result.Loading, actual = it)
                is Result.Success -> assertEquals(
                    expected = successfulOfItems, actual = it.data.map { item ->
                        item.toItemDTO()
                    }
                )
                is Result.Error -> fail(message = "Unexpected result type $it")
            }
        }
    }

    @Test
    fun `test fails API call`() = runTest {
        Mockito.`when`(apiService.getItems()).thenThrow(
            HttpException(Response.error<HttpException>(404, "".toResponseBody(contentType = null)))
        )
        apiRepositoryImpl.getItems().collect {
            when(it) {
                is Result.Loading -> assertEquals(Result.Loading, it)
                is Result.Success -> fail("Unexpected result type $it")
                is Result.Error -> assertEquals(DataError.NetworkError.NOT_FOUND, it.error)
            }
        }
    }

    @Test
    fun `test fails API call when other exception raise`() = runTest {
        Mockito.`when`(apiService.getItems()).thenThrow(RuntimeException())
        apiRepositoryImpl.getItems().collect {
            when(it) {
                is Result.Loading -> assertEquals(Result.Loading, it)
                is Result.Success -> fail("Unexpected result type $it")
                is Result.Error -> assertEquals(DataError.NetworkError.SOMETHING_WENT_WRONG_TRY_AGAIN_LATER, it.error)
            }
        }
    }

}