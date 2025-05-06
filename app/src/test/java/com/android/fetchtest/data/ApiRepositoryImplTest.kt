package com.android.fetchtest.data

import com.android.fetchtest.domain.DataError
import com.android.fetchtest.domain.Result
import junit.framework.TestCase.assertEquals
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
            Item(listId = 1, name = "okay 1", id = 1),
            Item(listId = 2, name = "okay 2", id = 2),
            Item(listId = 3, name = "okay 3", id = 3)
        )
        Mockito.`when`(apiService.getItems()).thenReturn(successfulOfItems)
        apiRepositoryImpl.getItems().collect {
            when(it) {
                is Result.Loading -> assertEquals(Result.Loading, it)
                is Result.Success -> assertEquals(successfulOfItems, it.data)
                is Result.Error -> fail("Unexpected result type $it")
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