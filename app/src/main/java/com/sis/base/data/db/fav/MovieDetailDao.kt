package com.sis.base.data.db.fav

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDetailDao {

    @Query("SELECT * FROM MovieDetailData WHERE imdbID=:movieId")
    fun getMovieDetail(movieId:String): MovieDetailData?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: MovieDetailData)

}