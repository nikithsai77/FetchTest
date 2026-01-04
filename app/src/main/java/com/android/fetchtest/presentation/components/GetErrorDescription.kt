package com.android.fetchtest.presentation.components

import com.android.fetchtest.R
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.android.fetchtest.domain.util.DataError

@Composable
fun DataError.getErrorDescription(): String {
    return when(this) {
        DataError.NetworkError.Conflict -> stringResource(id = R.string.Conflict)
        DataError.NetworkError.NOT_FOUND -> stringResource(id = R.string.NOT_FOUND)
        DataError.NetworkError.NO_INTERNET -> stringResource(id = R.string.NO_INTERNET)
        DataError.NetworkError.BAD_REQUEST -> stringResource(id = R.string.BAD_REQUEST)
        DataError.NetworkError.Forbidden -> stringResource(id = R.string.server_decline)
        DataError.NetworkError.SERVER_ERROR -> stringResource(id = R.string.SERVER_ERROR)
        DataError.NetworkError.Unauthorized -> stringResource(id = R.string.Unauthorized)
        DataError.NetworkError.TOO_MANY_REQUESTS -> stringResource(id = R.string.to_many_req)
        DataError.NetworkError.REQUEST_TIMEOUT -> stringResource(id = R.string.REQUEST_TIMEOUT)
        DataError.NetworkError.PAYLOAD_TOO_LARGE -> stringResource(id = R.string.PAYLOAD_TOO_LARGE)
        DataError.NetworkError.SERVICEUnAvailable -> stringResource(id = R.string.Service_UnAvailable)
        DataError.NetworkError.UNSUPPORTED_MEDIA_TYPE -> stringResource(id = R.string.UNSUPPORTED_MEDIA_TYPE)
        DataError.NetworkError.SOMETHING_WENT_WRONG_TRY_AGAIN_LATER -> stringResource(id = R.string.some_thing)
        DataError.NetworkError.REQUEST_NOT_ALLOWED -> stringResource(id = R.string.the_request_method_is_not_supported)
    }
}
