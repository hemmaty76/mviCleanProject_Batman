package com.sis.base.domain.repository

import androidx.paging.PagingData
import com.sis.base.domain.model.ItemMovieDto
import kotlinx.coroutines.flow.Flow

interface ItemsRepository {

    suspend fun getMovieItem(): Flow<PagingData<ItemMovieDto>>

}