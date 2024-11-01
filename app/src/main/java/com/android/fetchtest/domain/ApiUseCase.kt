package com.android.fetchtest.domain

import com.android.fetchtest.common.Resource
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException

class ApiUseCase(private val apiRepository: ApiRepository) {

    operator fun invoke() : Flow<Resource<Map<Int, List<Item>>>> = flow {
        try {
            emit(Resource.Loading)
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
            emit(Resource.Success(itemList = finalItemsList))
        }
        catch (e: IOException) {
            emit(Resource.Error(error = "Couldn't Reach Server, Check Your Internet Connection."))
        }
        catch (ex: Exception) {
            if (ex is CancellationException) throw  ex
            emit(Resource.Error(error = ex.message ?: "SomeThing Went Wrong Try Again Later!"))
        }
    }.flowOn(Dispatchers.Default)

}