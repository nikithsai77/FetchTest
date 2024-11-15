package com.android.fetchtest.domain

import com.android.fetchtest.common.UIState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException

class ApiUseCase(private val apiRepository: ApiRepository) {

    operator fun invoke() : Flow<UIState> = flow {
        try {
            emit(UIState.Loading)
            val fetchedItems = apiRepository.getItems()
            val finalItemsList = fetchedItems.filter {
                !it.name.isNullOrBlank()
            }.sortedBy {
                it.listId
            }.groupBy {
                it.listId
            }.toMutableMap()
            finalItemsList.forEach { (key, items) ->
                finalItemsList[key] = items.sortedBy { item ->
                    item.name!!.split(" ")[1].toInt()
                }
            }
            emit(UIState.Success(data = finalItemsList))
        }
        catch (e: IOException) {
            emit(UIState.Error(error = "Couldn't Reach Server, Check Your Internet Connection."))
        }
        catch (ex: Exception) {
            if (ex is CancellationException) throw  ex
            emit(UIState.Error(error = ex.message ?: "SomeThing Went Wrong Try Again Later!"))
        }
    }.flowOn(Dispatchers.Default)

}