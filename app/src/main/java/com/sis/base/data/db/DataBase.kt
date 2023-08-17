package com.sis.base.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sis.base.data.db.fav.MovieDetailDao
import com.sis.base.data.db.fav.MovieDetailData
import com.sis.base.data.db.movie.ItemsMovieDao
import com.sis.base.data.db.movie.ItemMovieData
import com.sis.base.data.db.movie.remoteKey.ItemRemoteKeys
import com.sis.base.data.db.movie.remoteKey.ItemRemoteKeysDao


@Database(entities = [ItemMovieData::class,MovieDetailData::class,ItemRemoteKeys::class], version = 1, exportSchema = false)
abstract class DataBase : RoomDatabase() {

    abstract fun everythingDao(): ItemsMovieDao
    abstract fun favDao(): MovieDetailDao
    abstract fun itemRemoteKeyDao(): ItemRemoteKeysDao


}