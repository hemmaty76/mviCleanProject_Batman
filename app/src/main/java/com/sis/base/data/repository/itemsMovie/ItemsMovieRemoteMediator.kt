package com.sis.base.data.repository.itemsMovie

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.sis.base.data.MyPreference
import com.sis.base.data.db.movie.ItemMovieData
import com.sis.base.data.db.movie.ItemsMovieDao
import com.sis.base.data.db.movie.remoteKey.ItemRemoteKeys
import com.sis.base.data.db.movie.remoteKey.ItemRemoteKeysDao
import com.sis.base.data.mapper.toDbModelList
import com.sis.base.data.remote.ApiService
import com.sis.base.data.remote.networkUtil.NetworkResponse
import com.sis.base.data.remote.networkUtil.getThrowable
import com.sis.base.utils.API_KEY
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class ItemsMovieRemoteMediator(
    private val apiService: ApiService,
    private val itemDao: ItemsMovieDao,
    private val remoteKeysDao: ItemRemoteKeysDao,
    private val myPreference: MyPreference,
) : RemoteMediator<Int, ItemMovieData>() {
    private val key = "itemsMovie"
    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS)
        return if (System.currentTimeMillis() - (remoteKeysDao.getCreationTime(key) ?: 0) < cacheTimeout) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, ItemMovieData>): ItemRemoteKeys? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { item ->
            remoteKeysDao.getRemoteKeyByItemID(item.imdbID,key)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, ItemMovieData>): ItemRemoteKeys? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { item ->
            remoteKeysDao.getRemoteKeyByItemID(item.imdbID,key)
        }
    }


    override suspend fun load(loadType: LoadType, state: PagingState<Int, ItemMovieData>): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                remoteKeysDao.clearRemoteKeys(key)
                1
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                prevKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                nextKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
        }
        when (val apiResponse = apiService.getItems(API_KEY, "batman", page)) {
            is NetworkResponse.Error -> {
                return MediatorResult.Error(apiResponse.error.getThrowable(apiResponse.message))
            }
            is NetworkResponse.Success -> {
                val items = apiResponse.data.data?:ArrayList()
                val endOfPaginationReached = items.isEmpty()
                if (loadType == LoadType.REFRESH) {
                    itemDao.clearAllItems()
                }
                val prevKey = if (page > 1) page - 1 else null
                val nextKey = if (endOfPaginationReached) null else page + 1
                val remoteKeys = items.map {
                    ItemRemoteKeys(it.imdbID, prevKey = prevKey, currentPage = page, nextKey = nextKey, key)
                }
                remoteKeysDao.insertAll(remoteKeys)
                itemDao.insertAll(items.toDbModelList())
                return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
            }
        }
    }

}