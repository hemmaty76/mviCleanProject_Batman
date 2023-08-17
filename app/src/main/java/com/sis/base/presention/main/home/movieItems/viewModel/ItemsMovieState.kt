package com.sis.base.presention.main.home.movieItems.viewModel

sealed class ItemsMovieState {
    object Idle : ItemsMovieState()
    data class RequestMovieDetail(val movieId:String) : ItemsMovieState()

}