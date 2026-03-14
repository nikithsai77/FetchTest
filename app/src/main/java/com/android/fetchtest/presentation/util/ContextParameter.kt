package com.android.fetchtest.presentation.util

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.stateIn
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted

context(viewModel: ViewModel)
fun <T> Flow<T>.stateInWhileSubScribed(initialValue: T) : StateFlow<T> {
    return this.stateIn(
        scope = viewModel.viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
        initialValue = initialValue
    )
}
