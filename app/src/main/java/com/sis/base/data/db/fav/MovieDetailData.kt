package com.sis.base.data.db.fav

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieDetailData (
    @PrimaryKey
    val imdbID: String,
    val title: String,
    val year: String,
    val rated: String,
    val released: String,
    val runtime: String,
    val genre: String,
    val director: String,
    val writer: String,
    val actor: String,
    val plot: String,
    val language: String,
    val country: String,
    val awards: String,
    val poster: String,
    val metascore: String,
    val imdbRating: String,
    val imdbVotes: String,
    val type: String,
)