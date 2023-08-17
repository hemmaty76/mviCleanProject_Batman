package com.sis.base.data.remote.networkUtil

sealed class NetworkResponse<out R : Any> {

    data class Success<T : Any>(val data: T) : NetworkResponse<T>()

    data class Error(val error: ApiError, val message: String?=null) : NetworkResponse<Nothing>()

}