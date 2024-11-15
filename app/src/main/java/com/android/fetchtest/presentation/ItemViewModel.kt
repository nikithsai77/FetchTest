package com.android.fetchtest.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.fetchtest.common.ClickEvent
import com.android.fetchtest.common.UIState
import com.android.fetchtest.domain.ApiUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ItemViewModel @Inject constructor(private val apiUseCase: ApiUseCase) : ViewModel() {
    private val _resource = MutableStateFlow<UIState>(UIState.Loading)
    val resource: StateFlow<UIState> = _resource.asStateFlow()

    init {
        fetchItems()
    }

    private fun fetchItems() {
        apiUseCase().onEach { newState ->
                      _resource.update {
                          newState
                      }
                  }.launchIn(viewModelScope)
    }

    fun onEvent(clickEvent: ClickEvent) {
        when(clickEvent) {
            ClickEvent.Retry -> fetchItems()
        }
    }

}