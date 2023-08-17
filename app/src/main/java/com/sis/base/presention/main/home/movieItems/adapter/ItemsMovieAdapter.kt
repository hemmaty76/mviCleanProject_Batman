package com.sis.base.presention.main.home.movieItems.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import com.sis.base.R
import com.sis.base.databinding.AdapterItemLayoutBinding
import com.sis.base.domain.model.ItemMovieDto
import com.sis.base.presention.base.adapter.BaseViewHolder


class ItemsMovieAdapter(var listener: Listener) : PagingDataAdapter<ItemMovieDto, ItemsMovieAdapter.ViewHolder>(ItemsDiffCallBack()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getItem(position), position)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.adapter_item_layout,
                parent,
                false
            )
        )
    }

    inner class ViewHolder(binding: AdapterItemLayoutBinding) : BaseViewHolder<AdapterItemLayoutBinding, ItemMovieDto>(binding) {

        fun onBind(item: ItemMovieDto?, position: Int) {
            item?.let {
                binding.item = item
                itemView.setOnClickListener {
                    listener.showDetail(position, item)
                }
            }
        }

    }

    interface Listener {
        fun showDetail(position: Int, itemDto: ItemMovieDto)
    }


}