package com.android.fetchtest.domain.util

sealed interface DataError {
    enum class NetworkError: DataError {
        Unauthorized,
        SERVER_ERROR,
        NO_INTERNET,
        BAD_REQUEST,
        NOT_FOUND,
        Conflict,
        Forbidden,
        REQUEST_TIMEOUT,
        TOO_MANY_REQUESTS,
        PAYLOAD_TOO_LARGE,
        SERVICE_UN_AVAILABLE,
        REQUEST_NOT_ALLOWED,
        UNSUPPORTED_MEDIA_TYPE,
        SOMETHING_WENT_WRONG_TRY_AGAIN_LATER
    }
}
