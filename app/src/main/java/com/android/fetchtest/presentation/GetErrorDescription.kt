package com.android.fetchtest.presentation

import com.android.fetchtest.R
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.android.fetchtest.domain.DataError

@Composable
fun DataError.getErrorDescription(): String {
    return when(this) {
        DataError.NetworkError.Conflict -> stringResource(R.string.Conflict)
        DataError.NetworkError.NOT_FOUND -> stringResource(R.string.NOT_FOUND)
        DataError.NetworkError.NO_INTERNET -> stringResource(R.string.NO_INTERNET)
        DataError.NetworkError.BAD_REQUEST -> stringResource(R.string.BAD_REQUEST)
        DataError.NetworkError.Forbidden -> stringResource(R.string.server_decline)
        DataError.NetworkError.SERVER_ERROR -> stringResource(R.string.SERVER_ERROR)
        DataError.NetworkError.Unauthorized -> stringResource(R.string.Unauthorized)
        DataError.NetworkError.TOO_MANY_REQUESTS -> stringResource(R.string.to_many_req)
        DataError.NetworkError.REQUEST_TIMEOUT -> stringResource(R.string.REQUEST_TIMEOUT)
        DataError.NetworkError.PAYLOAD_TOO_LARGE -> stringResource(R.string.PAYLOAD_TOO_LARGE)
        DataError.NetworkError.SERVICEUnAvailable -> stringResource(R.string.Service_UnAvailable)
        DataError.NetworkError.UNSUPPORTED_MEDIA_TYPE -> stringResource(R.string.UNSUPPORTED_MEDIA_TYPE)
        DataError.NetworkError.SOMETHING_WENT_WRONG_TRY_AGAIN_LATER -> stringResource(R.string.some_thing)
        DataError.NetworkError.REQUEST_NOT_ALLOWED -> stringResource(R.string.the_request_method_is_not_supported)
    }
}
