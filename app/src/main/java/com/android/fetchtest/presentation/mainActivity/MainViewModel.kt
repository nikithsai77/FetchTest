package com.android.fetchtest.presentation.mainActivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.fetchtest.domain.ApiUseCase
import com.android.fetchtest.domain.DataError
import com.android.fetchtest.domain.FetchItem
import com.android.fetchtest.domain.Result
import com.android.fetchtest.presentation.ClickEvent
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
class MainViewModel @Inject constructor(private val apiUseCase: ApiUseCase) : ViewModel() {
    private var job: Job? = null
    private val _resource = MutableStateFlow<Result<Map<Int, List<FetchItem>>, DataError>>(value = Result.Loading)
    val resource: StateFlow<Result<Map<Int, List<FetchItem>>, DataError>> = _resource.onStart { fetchItems() }.stateIn(scope = viewModelScope, started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L), initialValue = Result.Loading)

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