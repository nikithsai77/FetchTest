package com.android.fetchtest.data

import com.android.fetchtest.MainDispatcherRule
import com.android.fetchtest.domain.Repository
import com.android.fetchtest.domain.model.FetchItem
import com.android.fetchtest.domain.useCase.ApiUseCase
import com.android.fetchtest.domain.util.DataError
import com.android.fetchtest.domain.util.Result
import com.android.fetchtest.presentation.mainActivity.MainViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ItemViewModelTest {
    @get:Rule
    private val mainDispatcherRule = MainDispatcherRule()
    @MockK
    private lateinit var repository: Repository
    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        mainViewModel = MainViewModel(ApiUseCase(repository))
    }

    @Test
    fun testSuccessCase() = runTest {
         coEvery { repository.getItems() } returns flowOf(Result.Loading, Result.Success(data = getItems()))
         val results = mainViewModel.resource.take(count = 2).toList()
         assertEquals(2, results.size)
         assertEquals(Result.Loading, results[0])
         assertEquals(Result.Success(data = getSuccessMap()), results[1])
    }

    @Test
    fun testFailureCase() = runTest {
        coEvery { repository.getItems() } returns flowOf(Result.Loading, Result.Error(error = DataError.NetworkError.SERVER_ERROR))
        val result = mainViewModel.resource.take(count = 2).toList()
        assertEquals(2, result.size)
        assertEquals(Result.Loading, result[0])
        assertEquals(Result.Error(error = DataError.NetworkError.SERVER_ERROR), result[1])
    }

}

private fun getSuccessMap(): Map<Int, List<FetchItem>> {
    return mapOf(1 to listOf(FetchItem(listId = 1, name = "okay 1", id = 1), FetchItem(listId = 1, name = "okay 1", id = 2), FetchItem(listId = 1, name = "okay 1", id = 3)), 2 to listOf(FetchItem(listId = 2, name = "okay 1", id = 1), FetchItem(listId = 2, name = "okay 2", id = 2)))
}

private fun getItems() : List<FetchItem> {
    return listOf(FetchItem(listId = 1, name = "okay 1", id = 1), FetchItem(listId = 2, name = "okay 1", id = 1), FetchItem(listId = 1, name = "okay 1", id = 2), FetchItem(listId = 1, name = "okay 1", id = 3), FetchItem(listId = 2, name = "okay 2", id = 2))
}
