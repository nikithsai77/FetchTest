package com.android.fetchtest.presentation

sealed interface ClickEvent {
    data object Retry: ClickEvent
}