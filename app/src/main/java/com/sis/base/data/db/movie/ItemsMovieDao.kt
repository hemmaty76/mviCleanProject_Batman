package com.sis.base.data.db.movie

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ItemsMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(items: List<ItemMovieData>)

    @Query("Select * From ItemMovieData")
    fun getItems(): PagingSource<Int, ItemMovieData>

    @Query("Delete From ItemMovieData")
    fun clearAllItems()

}