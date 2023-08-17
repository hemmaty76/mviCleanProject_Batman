package com.sis.base.data.db.movie.remoteKey

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ItemRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(remoteKey: List<ItemRemoteKeys>)

    @Query("Select * From remote_key Where id = :id AND requestKey=:requestKey")
    fun getRemoteKeyByItemID(id: String, requestKey: String): ItemRemoteKeys?

    @Query("Delete From remote_key WHERE requestKey =:requestKey")
    fun clearRemoteKeys(requestKey: String)

    @Query("Select created_at From remote_key WHERE requestKey =:requestKey Order By created_at DESC LIMIT 1")
    fun getCreationTime(requestKey: String): Long?

}