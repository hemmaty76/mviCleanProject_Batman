package com.sis.base.data.remote.model.base

import com.google.gson.annotations.SerializedName

data class ListResponseModel<R>(
    @SerializedName("Search")
    val data: ArrayList<R>?
) : ResponseModel()