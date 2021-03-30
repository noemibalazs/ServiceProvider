package com.noemi.android.serviceprovider.data

data class Resource<out T>(val status: Status, val data: T?, val message: String?, val code: Int?) {

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data = data, message = null, code = 200)
        }

        fun <T> isLoading(): Resource<T> {
            return Resource(Status.LOADING, data = null, message = null, code = null)
        }

        fun <T> failure(data: T?, message: String?, code: Int?): Resource<T> {
            return Resource(Status.FAILURE, data = data, message = message, code = code)
        }
    }
}

enum class Status {
    SUCCESS,
    LOADING,
    FAILURE
}