package com.sis.base.data.remote.networkUtil

import com.google.gson.JsonParseException
import org.json.JSONException
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException


enum class ApiError {
    ERROR_204, ERROR_400, ERROR_401, ERROR_404, ERROR_405, ERROR_429,
    ERROR_500, ERROR_TIME_OUT, ERROR_DISCONNECTED, ERROR_PARSE, ERROR_UNKNOWN,
    ERROR_NO_CONTENT, ERROR_CODE_0
}

fun ApiError.getStringMessage(): String? {
    return when (this) {
        ApiError.ERROR_204, ApiError.ERROR_NO_CONTENT -> "There is no data"
        ApiError.ERROR_404 -> "The desired information was not found"
        ApiError.ERROR_429 -> "The number of your allowed requests has ended!!"
        ApiError.ERROR_500 -> "Server error! Please try again later"
        ApiError.ERROR_DISCONNECTED -> "Internet connection error"
        ApiError.ERROR_TIME_OUT -> "No response received"
        else -> null
    }
}

fun ApiError.getThrowable(messageError: String?=null): Throwable {
    return Throwable(getStringMessage() ?: messageError)
}

fun Throwable.getApiError(): ApiError {
    return when (this) {
        is UnknownHostException, is ConnectException -> ApiError.ERROR_DISCONNECTED
        is JsonParseException, is JSONException -> ApiError.ERROR_PARSE
        is TimeoutException, is SocketTimeoutException -> ApiError.ERROR_TIME_OUT
        else -> ApiError.ERROR_UNKNOWN
    }
}


fun <T> Response<T>.getApiError(): ApiError {
    return when (this.code()) {
        204 -> ApiError.ERROR_204
        400 -> ApiError.ERROR_400
        401 -> ApiError.ERROR_401
        404 -> ApiError.ERROR_404
        405 -> ApiError.ERROR_405
        429 -> ApiError.ERROR_429
        500 -> ApiError.ERROR_500
        else -> ApiError.ERROR_UNKNOWN
    }
}