package com.sis.base.domain.repository

import com.sis.base.domain.model.MovieDetailDto
import kotlinx.coroutines.flow.Flow

interface MovieDetailRepository {


    suspend fun getMovieDetail(movieId: String): Flow<MovieDetailDto>



}