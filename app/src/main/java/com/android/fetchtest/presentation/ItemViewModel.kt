package com.android.fetchtest.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.fetchtest.data.Item
import com.android.fetchtest.domain.ApiUseCase
import com.android.fetchtest.domain.DataError
import com.android.fetchtest.domain.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ItemViewModel @Inject constructor(private val apiUseCase: ApiUseCase) : ViewModel() {
    private var job: Job? = null
    private val _resource = MutableStateFlow<Result<Map<Int, List<Item>>, DataError>>(Result.Loading)
    val resource: StateFlow<Result<Map<Int, List<Item>>, DataError>> = _resource.onStart { fetchItems() }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L), Result.Loading)

    private fun fetchItems() {
        if (job == null) {
            job = apiUseCase().onEach { newState ->
                _resource.update {
                    newState
                }
            }.launchIn(viewModelScope)
        }
    }

    fun onEvent(clickEvent: ClickEvent) {
        when(clickEvent) {
            ClickEvent.Retry -> {
                job = null
                fetchItems()
            }
        }
    }

}