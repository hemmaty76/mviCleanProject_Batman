package com.sis.base.data.mapper

import com.sis.base.data.db.fav.MovieDetailData
import com.sis.base.data.db.movie.ItemMovieData
import com.sis.base.data.remote.model.ItemMovieResponseModel
import com.sis.base.data.remote.model.MovieDetailResponseModel
import com.sis.base.domain.model.ItemMovieDto
import com.sis.base.domain.model.MovieDetailDto


fun ItemMovieResponseModel.toDbModel() = ItemMovieData(
    imdbID = this.imdbID,
    year = this.year,
    title = this.title,
    type = this.type,
    poster = this.poster
)

fun ItemMovieData.toDto() = ItemMovieDto(
    imdbID = this.imdbID,
    year = this.year,
    title = this.title,
    type = this.type,
    poster = this.poster
)

fun MovieDetailResponseModel.toDbModel() = MovieDetailData(
    imdbID = this.imdbID,
    title = this.title,
    year = this.year,
    rated = this.rated,
    released = this.released,
    runtime = this.runtime,
    genre = this.genre,
    director = this.director,
    writer = this.writer,
    actor = this.actors,
    plot = this.plot,
    language = this.language,
    country = this.country,
    awards = this.awards,
    poster = this.poster,
    metascore = this.metascore,
    imdbRating = this.imdbRating,
    imdbVotes = this.imdbVotes,
    type = this.type,
)

fun MovieDetailData.toDto() = MovieDetailDto(
    title = this.title,
    year = this.year,
    rated = this.rated,
    released = this.released,
    runtime = this.runtime,
    genre = this.genre,
    director = this.director,
    writer = this.writer,
    actor = this.actor,
    plot = this.plot,
    language = this.language,
    country = this.country,
    awards = this.awards,
    poster = this.poster,
    metascore = this.metascore,
    imdbRating = this.imdbRating,
    imdbVotes = this.imdbVotes,
    type = this.type,
)

fun List<ItemMovieResponseModel>.toDbModelList(): List<ItemMovieData> = this.map { it.toDbModel() }



