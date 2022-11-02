package com.example.testapp.network.model

data class Resource<out T>(
    val status: Status,
    val data: T?,
    val message: String?,
    val throwable: Throwable? = null
) {
    companion object {
        fun <T> success(data: T): Resource<T> =
            Resource(status = Status.SUCCESS, data = data, message = null)

        fun <T> error(data: T?, message: String): Resource<T> =
            Resource(status = Status.ERROR, data = data, message = message)


        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }

        fun <T> error500(throwable: Throwable? = null): Resource<T> {
            return Resource(Status.ERROR_500, null, null, throwable)
        }

        fun <T> networkFailed(throwable: Throwable? = null): Resource<T> =
            Resource(status = Status.NETWORK_FAILED, null, null, throwable = throwable)

//        fun <T> cached(data: T?, date: Date?): Resource<T> {
//            return Resource(status = Status.CACHED, data = data, date = date)
//        }

        fun <T> reAuthenticate(): Resource<T> {
            return Resource(Status.REAUTH, data = null, message = null)
        }

        fun <T> logout(): Resource<T> {
            return Resource(Status.LOGOUT, data = null, message = null)
        }
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING,
    NETWORK_FAILED,
    ERROR_500,
    CACHED,
    REAUTH,
    LOGOUT
}