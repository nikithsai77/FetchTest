package com.android.fetchtest.presentation

sealed interface OnEvent {
    data object Retry: OnEvent
}
