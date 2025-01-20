package com.android.fetchtest.common

sealed class ClickEvent {
    data object Retry: ClickEvent()
}