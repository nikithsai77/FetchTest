package com.android.fetchtest.domain.useCase

import com.android.fetchtest.domain.util.DataError
import com.android.fetchtest.domain.model.FetchItem
import com.android.fetchtest.domain.Repository
import com.android.fetchtest.domain.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class ApiUseCase(private val repository: Repository) {

    operator fun invoke() : Flow<Result<Map<Int, List<FetchItem>>, DataError>> = flow {
        val res = repository.getItems()
        res.collect { result ->
            when (result) {
                is Result.Loading -> emit(value = result)
                is Result.Error -> emit(value = result)
                is Result.Success -> {
                    val finalItemsList = withContext(context = Dispatchers.Default) {
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
                    emit(value = Result.Success(data = finalItemsList))
                }
            }
        }
    }

}
