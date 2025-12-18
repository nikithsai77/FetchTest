package com.android.fetchtest.domain

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
        SERVICEUnAvailable,
        REQUEST_NOT_ALLOWED,
        UNSUPPORTED_MEDIA_TYPE,
        SOMETHING_WENT_WRONG_TRY_AGAIN_LATER
    }
}
