package com.android.fetchtest.data

import com.android.fetchtest.domain.ApiUseCase
import com.android.fetchtest.domain.DataError
import com.android.fetchtest.domain.FetchItem
import com.android.fetchtest.domain.Repository
import com.android.fetchtest.domain.Result
import com.android.fetchtest.presentation.mainActivity.MainViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class ItemViewModelTest {
     @Mock
     private lateinit var repository: Repository

     @Before
     fun setUp() {
         MockitoAnnotations.initMocks(this)
     }

     @Test
     fun testSuccessCase() = runTest {
         Dispatchers.setMain(UnconfinedTestDispatcher())
         Mockito.`when`(repository.getItems()).thenReturn(flowOf(Result.Loading, Result.Success(data = getItems())))
         val viewModel = MainViewModel(ApiUseCase(repository))
         val results = viewModel.resource.take(count = 2).toList()
         assertEquals(2, results.size)
         assertEquals(Result.Loading, results[0])
         assertEquals(Result.Success(data = getSuccessMap()), results[1])
         Dispatchers.resetMain()
     }

    @Test
    fun testFailureCase() = runTest {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        Mockito.`when`(repository.getItems()).thenReturn(flowOf(Result.Error(error = DataError.NetworkError.SERVER_ERROR)))
        val viewModel = MainViewModel(ApiUseCase(repository))
        val result = viewModel.resource.take(count = 1).toList()
        assertEquals(1, result.size)
        assertEquals(Result.Error(error = DataError.NetworkError.SERVER_ERROR), result[0])
        Dispatchers.resetMain()
    }

}

private fun getSuccessMap(): Map<Int, List<FetchItem>> {
    return mapOf(1 to listOf(FetchItem(listId = 1, name = "okay 1", id = 1), FetchItem(listId = 1, name = "okay 1", id = 2), FetchItem(listId = 1, name = "okay 1", id = 3)), 2 to listOf(FetchItem(listId = 2, name = "okay 1", id = 1), FetchItem(listId = 2, name = "okay 2", id = 2)))
}

private fun getItems() : List<FetchItem> {
    return listOf(FetchItem(listId = 1, name = "okay 1", id = 1), FetchItem(listId = 2, name = "okay 1", id = 1), FetchItem(listId = 1, name = "okay 1", id = 2), FetchItem(listId = 1, name = "okay 1", id = 3), FetchItem(listId = 2, name = "okay 2", id = 2))
}
