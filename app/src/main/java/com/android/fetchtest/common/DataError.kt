package com.android.fetchtest.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.android.fetchtest.R

sealed interface DataError {
    enum class NetworkError: DataError {
        REQUEST_TIMEOUT,
        TOO_MANY_REQUESTS,
        Unauthorized,
        NO_INTERNET,
        PAYLOAD_TOO_LARGE,
        BAD_REQUEST,
        SERVER_ERROR,
        NOT_FOUND,
        UNKNOWN,
        SERVICEUnAvailable,
        SOMETHING_WENT_WRONG_TRY_AGAIN_LATER
    }
}

@Composable
fun DataError.getErrorDescription(): String {
    val context = LocalContext.current
    return when(this) {
        DataError.NetworkError.REQUEST_TIMEOUT -> context.getString(R.string.REQUEST_TIMEOUT)
        DataError.NetworkError.TOO_MANY_REQUESTS -> context.getString(R.string.to_many_req)
        DataError.NetworkError.Unauthorized -> context.getString(R.string.Unauthorized)
        DataError.NetworkError.NO_INTERNET -> context.getString(R.string.NO_INTERNET)
        DataError.NetworkError.PAYLOAD_TOO_LARGE -> context.getString(R.string.PAYLOAD_TOO_LARGE)
        DataError.NetworkError.BAD_REQUEST -> context.getString(R.string.BAD_REQUEST)
        DataError.NetworkError.SERVER_ERROR -> context.getString(R.string.SERVER_ERROR)
        DataError.NetworkError.NOT_FOUND -> context.getString(R.string.NOT_FOUND)
        DataError.NetworkError.SERVICEUnAvailable -> context.getString(R.string.SERVICEUnAvailable)
        DataError.NetworkError.UNKNOWN -> context.getString(R.string.UNKNOWN)
        DataError.NetworkError.SOMETHING_WENT_WRONG_TRY_AGAIN_LATER -> context.getString(R.string.some_thing)
    }
}