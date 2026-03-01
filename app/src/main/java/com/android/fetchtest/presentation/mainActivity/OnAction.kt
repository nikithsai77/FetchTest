package com.android.fetchtest.presentation.mainActivity

sealed interface OnAction {
    data object Retry: OnAction
}
