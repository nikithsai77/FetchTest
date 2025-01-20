package com.android.fetchtest.domain

import com.android.fetchtest.common.DataError
import com.android.fetchtest.common.Result
import com.android.fetchtest.data.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class ApiUseCase(private val repository: Repository) {

    operator fun invoke() : Flow<Result<Map<Int, List<Item>>, DataError>> = flow {
        val res = repository.getItems()
        res.collect { result ->
            when(result) {
                is Result.Loading -> emit(result)
                is Result.Error -> emit(Result.Error(error = result.error))
                is Result.Success -> {
                    val finalItemsList = withContext(Dispatchers.Default) {
                        val map = result.data.filter {
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
                    emit(Result.Success(data = finalItemsList))
                }
            }
        }
    }

}