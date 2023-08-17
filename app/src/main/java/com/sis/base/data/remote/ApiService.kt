package com.sis.base.data.remote

import com.sis.base.data.remote.model.ItemMovieResponseModel
import com.sis.base.data.remote.model.MovieDetailResponseModel
import com.sis.base.data.remote.model.base.ListResponseModel
import com.sis.base.data.remote.networkUtil.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/")
    suspend fun getItems(
        @Query("apiKey") apiKey: String,
        @Query("s") searchText: String,
        @Query("page") page: Int,
    ) : NetworkResponse<ListResponseModel<ItemMovieResponseModel>>

    @GET("/")
    suspend fun getItemDetail(
        @Query("apiKey") apiKey: String,
        @Query("i") movieId: String
    ) : NetworkResponse<MovieDetailResponseModel>

}