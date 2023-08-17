package com.sis.base.di

import com.sis.base.data.db.DataBase
import com.sis.base.data.db.fav.MovieDetailDao
import com.sis.base.data.db.movie.ItemsMovieDao
import com.sis.base.data.db.movie.remoteKey.ItemRemoteKeysDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * DaoModule is to provide di for Room DAOs interfaces
 *
 * @author mojtaba hemmati sis
 */
@Module
@InstallIn(SingletonComponent::class)
class DaoModule {

    @Provides
    fun provideEverythingDao(mviDatabase: DataBase): ItemsMovieDao = mviDatabase.everythingDao()

    @Provides
    fun provideRemoteKeyDao(mviDatabase: DataBase): ItemRemoteKeysDao = mviDatabase.itemRemoteKeyDao()

    @Provides
    fun provideFavDao(mviDatabase: DataBase): MovieDetailDao = mviDatabase.favDao()



}