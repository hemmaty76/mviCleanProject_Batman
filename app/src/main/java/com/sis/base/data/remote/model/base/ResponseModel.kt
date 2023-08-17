package com.sis.base.data.remote.model.base

import com.google.gson.annotations.SerializedName

open class ResponseModel {
    @SerializedName("Response")
    lateinit var response: ResponseState
    @SerializedName("Error")
    val error: String? = null
}