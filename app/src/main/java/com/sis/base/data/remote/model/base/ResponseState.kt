package com.sis.base.data.remote.model.base

import com.google.gson.annotations.SerializedName

enum class ResponseState(val isSuccess:Boolean) {

    @SerializedName("True")
    TRUE(true),

    @SerializedName("False")
    FALSE(false),

}