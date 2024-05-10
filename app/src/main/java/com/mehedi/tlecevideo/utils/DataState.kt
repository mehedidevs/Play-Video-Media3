package com.mehedi.tlecevideo.utils

sealed class DataState<T>(
    var message: String? = null,
    var data: T? = null
) {
    class Loading<T> : DataState<T>()
    class Success<T>(mData: T?) : DataState<T>(data = mData)
    class Error<T>(message: String?) : DataState<T>(message)
}

