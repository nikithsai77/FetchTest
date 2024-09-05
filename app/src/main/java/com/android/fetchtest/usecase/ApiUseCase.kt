package com.android.fetchtest.usecase

import com.android.fetchtest.api.ApiRepository
import com.android.fetchtest.common.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ApiUseCase @Inject constructor(private val apiRepository: ApiRepository) {

    operator fun invoke() : Flow<UIState> = flow {
        try {
            emit(UIState.Loading)
            val fetchedItems = apiRepository.getItems()
            val finalItemsList = fetchedItems.filter {
                !it.name.isNullOrBlank()
            }.sortedBy {
                it.listId
            }.groupBy {
                item -> item.listId
            }.toMutableMap()
            finalItemsList.forEach { (key, items) ->
                finalItemsList[key] = items.sortedBy { item ->
                    item.name!!.split(" ")[1].toInt()
                }
            }
            emit(UIState.Success(itemList = finalItemsList))
        }
        catch (ex: Exception) {
            emit(UIState.Error(error = ex.message ?: "SomeThing Went Wrong Try Again Later!"))
        }
    }.flowOn(Dispatchers.IO)

}