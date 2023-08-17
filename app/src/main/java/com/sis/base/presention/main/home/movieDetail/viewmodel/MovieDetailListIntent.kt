package com.sis.base.presention.main.home.movieDetail.viewmodel

import com.sis.base.presention.base.BaseIntent

sealed class MovieDetailListIntent : BaseIntent() {
    data class RequestItemDetail(val movieId:String) : MovieDetailListIntent()
}
