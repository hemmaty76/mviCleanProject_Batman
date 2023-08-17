package com.sis.base.data.repository.itemsMovie

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.sis.base.data.MyPreference
import com.sis.base.data.db.movie.ItemsMovieDao
import com.sis.base.data.db.movie.remoteKey.ItemRemoteKeysDao
import com.sis.base.data.mapper.toDto
import com.sis.base.data.remote.ApiService
import com.sis.base.domain.model.ItemMovieDto
import com.sis.base.domain.repository.ItemsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ItemsMovieRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val itemMovieData: ItemsMovieDao,
    private val remoteKeysDao: ItemRemoteKeysDao,
    private val myPreference: MyPreference
) : ItemsRepository {

    @Suppress("OPT_IN_IS_NOT_ENABLED")
    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getMovieItem(): Flow<PagingData<ItemMovieDto>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                itemMovieData.getItems()
            },
            remoteMediator = ItemsMovieRemoteMediator(apiService, itemMovieData, remoteKeysDao,myPreference)
        ).flow.map { pagingData ->
            pagingData.map {
                it.toDto()
            }
        }
    }




}