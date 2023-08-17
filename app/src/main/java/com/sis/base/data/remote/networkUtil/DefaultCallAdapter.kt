package com.sis.base.data.remote.networkUtil

import com.sis.base.data.remote.model.base.ResponseModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Converter
import java.lang.reflect.Type

class DefaultCallAdapter<R : ResponseModel>(
    private val responseType: Type,
) : CallAdapter<R, Call<NetworkResponse<R>>> {

    override fun responseType(): Type = responseType

    override fun adapt(call: Call<R>): Call<NetworkResponse<R>> {
        return NetworkResponseCall(call)
    }

}