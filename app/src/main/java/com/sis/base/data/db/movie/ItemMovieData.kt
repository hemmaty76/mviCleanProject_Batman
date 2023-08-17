package com.sis.base.data.db.movie

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ItemMovieData(
    @PrimaryKey
    var imdbID: String,
    var year: String,
    var title: String,
    var type: String,
    var poster: String
)