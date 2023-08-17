package com.sis.base.data.repository

import com.sis.base.data.db.fav.MovieDetailDao
import com.sis.base.data.mapper.toDbModel
import com.sis.base.data.mapper.toDto
import com.sis.base.data.remote.ApiService
import com.sis.base.data.remote.networkUtil.NetworkResponse
import com.sis.base.data.remote.networkUtil.getThrowable
import com.sis.base.domain.model.MovieDetailDto
import com.sis.base.domain.repository.MovieDetailRepository
import com.sis.base.utils.API_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieDetailRepositoryImpl @Inject constructor(
    private val movieDetailDao: MovieDetailDao,
    private val apiService: ApiService,
) : MovieDetailRepository {

    override suspend fun getMovieDetail(movieId: String): Flow<MovieDetailDto> = flow {
        apiService.getItemDetail(API_KEY, movieId).also {
            when (it) {
                is NetworkResponse.Success -> {
                    it.data.toDbModel().let { movieDetailDataDb ->
                        movieDetailDao.insert(movieDetailDataDb)
                        emit(movieDetailDataDb.toDto())
                    }
                }

                is NetworkResponse.Error -> {
                    movieDetailDao.getMovieDetail(movieId)?.let { movieDetailDataDb ->
                        emit(movieDetailDataDb.toDto())
                    } ?: run {
                        emit(throw it.error.getThrowable())
                    }
                }
            }
        }
    }


}