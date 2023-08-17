package com.sis.base.data.db.movie.remoteKey

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_key")
data class ItemRemoteKeys(
    @PrimaryKey val id: String,
    val prevKey: Int?,
    val currentPage: Int,
    val nextKey: Int?,
    val requestKey:String,
    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis()
)