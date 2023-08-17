package com.sis.base.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sis.base.databinding.AdapterStateItemFooterBinding

class ItemsStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<ItemsStateAdapter.ItemLoadStateViewHolder>() {

    override fun onBindViewHolder(holder: ItemLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): ItemLoadStateViewHolder {
        return ItemLoadStateViewHolder(AdapterStateItemFooterBinding.inflate(LayoutInflater.from(parent.context),parent,false),retry)
    }

    inner class ItemLoadStateViewHolder(
        private val binding: AdapterStateItemFooterBinding,
        retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {


        init {
            binding.btnRetry.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                binding.tvErrorDescription.text = loadState.error.localizedMessage
            }
            binding.progressLoadMore.root.isVisible = loadState is LoadState.Loading
            binding.btnRetry.isVisible = loadState is LoadState.Error
            binding.tvErrorDescription.isVisible = loadState is LoadState.Error
        }


    }

}