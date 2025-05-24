package com.android.fetchtest.presentation.mainActivity

import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.fetchtest.data.Item
import com.android.fetchtest.domain.Result
import com.android.fetchtest.domain.DataError
import com.android.fetchtest.domain.ApiUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import com.android.fetchtest.presentation.ClickEvent

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