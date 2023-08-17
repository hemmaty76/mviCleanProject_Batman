package com.sis.base.di

import com.sis.base.data.repository.MovieDetailRepositoryImpl
import com.sis.base.data.repository.itemsMovie.ItemsMovieRepositoryImpl
import com.sis.base.domain.repository.MovieDetailRepository
import com.sis.base.domain.repository.ItemsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * RepositoryModule is to bind di for all repositories
 *
 * @author mojtaba hemmati sis
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindFavItemsRepository(nameRepositoryImpl: MovieDetailRepositoryImpl): MovieDetailRepository
    @Binds
    abstract fun bindItemsRepository(nameRepositoryImpl: ItemsMovieRepositoryImpl): ItemsRepository

}