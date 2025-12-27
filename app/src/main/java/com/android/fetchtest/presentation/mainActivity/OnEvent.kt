package com.android.fetchtest.presentation.mainActivity

sealed interface OnEvent {
    data object Retry: OnEvent
}
