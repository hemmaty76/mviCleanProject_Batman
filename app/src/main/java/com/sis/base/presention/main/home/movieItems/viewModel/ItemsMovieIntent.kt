package com.sis.base.presention.main.home.movieItems.viewModel

import com.sis.base.presention.base.BaseIntent

sealed class ItemsMovieIntent :BaseIntent(){
    object GetItemList : ItemsMovieIntent()
}
