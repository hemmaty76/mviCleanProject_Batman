package com.sis.base.presention.main.home.movieItems.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sis.base.domain.model.ItemMovieDto
import com.sis.base.domain.repository.ItemsRepository
import com.sis.base.presention.base.BaseViewModel
import com.sis.base.presention.main.home.movieItems.adapter.ItemsMovieAdapter
import com.sis.base.utils.ItemsStateAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemsMovieViewModel @Inject constructor(
    private val itemRepository: ItemsRepository
) : BaseViewModel<ItemsMovieIntent>() {

    val dataState = MutableLiveData<ItemsMovieState>(ItemsMovieState.Idle)

    private var _adapter: ItemsMovieAdapter = ItemsMovieAdapter(object : ItemsMovieAdapter.Listener {
        override fun showDetail(position: Int, itemDto: ItemMovieDto) {
            dataState.postValue(ItemsMovieState.RequestMovieDetail(itemDto.imdbID))
        }
    }).apply {
        addLoadStateListener { loadState -> uiState.postValue(analiseState(loadState, itemCount)) }
    }

    val concatAdapter = _adapter.withLoadStateHeaderAndFooter(ItemsStateAdapter { retry() },
        ItemsStateAdapter { retry() })

    fun retry() {
        _adapter.retry()
    }
    private suspend fun getItemList() {
        itemRepository.getMovieItem().collectLatest {
            _adapter.submitData(it)
        }
    }
    override fun onTriggerEvent(eventType: ItemsMovieIntent) {
        viewModelScope.launch(Dispatchers.IO) {
            when (eventType) {
                is ItemsMovieIntent.GetItemList -> getItemList()
            }
        }
    }
    override fun isFetchData(): Boolean = _adapter.itemCount > 0

}