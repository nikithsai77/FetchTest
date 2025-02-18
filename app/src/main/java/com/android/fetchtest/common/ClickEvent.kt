package com.android.fetchtest.common

sealed interface ClickEvent {
    data object Retry: ClickEvent
}