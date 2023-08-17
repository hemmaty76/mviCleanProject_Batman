package com.sis.base.presention.main.home.movieItems.adapter

import androidx.recyclerview.widget.DiffUtil
import com.sis.base.domain.model.ItemMovieDto

class ItemsDiffCallBack : DiffUtil.ItemCallback<ItemMovieDto>() {
    override fun areItemsTheSame(oldItem: ItemMovieDto, newItem: ItemMovieDto): Boolean {
        return oldItem.imdbID == newItem.imdbID
    }

    override fun areContentsTheSame(oldItem: ItemMovieDto, newItem: ItemMovieDto): Boolean {
        return oldItem.imdbID == newItem.imdbID
    }
}