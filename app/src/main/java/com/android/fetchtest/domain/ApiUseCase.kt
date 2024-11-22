package com.android.fetchtest.domain

import com.android.fetchtest.common.Resource
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.io.IOException

class ApiUseCase(private val apiRepository: ApiRepository) {

    operator fun invoke() : Flow<Resource<Map<Int, List<Item>>>> = flow {
        try {
            emit(Resource.Loading)
            val fetchedItems = apiRepository.getItems()
            val finalItemsList = withContext(Dispatchers.Default) {
                val map = fetchedItems.filter {
                    ensureActive()
                    !it.name.isNullOrBlank()
                }.sortedBy {
                    ensureActive()
                    it.listId
                }.groupBy {
                    ensureActive()
                    it.listId
                }.toMutableMap()
                map.forEach { (key, items) ->
                    ensureActive()
                  map[key] = items.sortedBy { item ->
                     ensureActive()
                     item.name!!.split(" ")[1].toInt()
                  }
                }
                map
            }
            emit(Resource.Success(itemList = finalItemsList))
        }
        catch (ex: Exception) {
            if (ex is CancellationException) throw  ex
            val errorMessage = if (ex is IOException) "Couldn't Reach Server, Check Your Internet Connection"
            else ex.message ?: "SomeThing Went Wrong Try Again Later!"
            emit(Resource.Error(error = errorMessage))
        }
    }

}