package com.sis.base.data.remote.networkUtil

import com.sis.base.data.remote.model.base.ResponseModel
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class NetworkResponseCall<R : ResponseModel>(
    private val representative: Call<R>
) : Call<NetworkResponse<R>> {
    override fun isExecuted() = representative.isExecuted
    override fun clone() = NetworkResponseCall(representative.clone())
    override fun isCanceled() = representative.isCanceled
    override fun cancel() = representative.cancel()
    override fun request(): Request = representative.request()
    override fun timeout(): Timeout = representative.timeout()
    override fun execute(): Response<NetworkResponse<R>> {
        throw UnsupportedOperationException("NetworkResponseCall doesn't support execute")
    }

    override fun enqueue(callback: Callback<NetworkResponse<R>>) {
        return representative.enqueue(object : Callback<R> {
            override fun onResponse(call: Call<R>, response: Response<R>) {
                val body = response.body()
                if (response.isSuccessful) {
                    if (body != null) {
                        callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(getNetworkResponseFromBody(body))
                        )
                    } else {
                        callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(NetworkResponse.Error(ApiError.ERROR_NO_CONTENT))
                        )
                    }
                } else {
                    callback.onResponse(
                        this@NetworkResponseCall,
                        Response.success(NetworkResponse.Error(response.getApiError()))
                    )
                }
            }
            override fun onFailure(call: Call<R>, t: Throwable) {
                callback.onResponse(
                    this@NetworkResponseCall,
                    Response.success(NetworkResponse.Error(t.getApiError()))
                )
            }
        })
    }

    private fun getNetworkResponseFromBody(body: R): NetworkResponse<R> {
        return try {
            if (body.response?.isSuccess==true) {
                NetworkResponse.Success(body)
            } else {
                NetworkResponse.Error(ApiError.ERROR_CODE_0,body.error)
            }
        } catch (ex: Exception) {
            NetworkResponse.Error(ApiError.ERROR_PARSE, ex.message)
        }
    }

}


