package com.sis.base.presention.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.sis.base.utils.UiState

/**
 * BaseViewModel class the parent of all view models.
 *
 * @author mojtaba hemmati sis
 */
abstract class BaseViewModel<T : BaseIntent> : ViewModel() {

    var pageIndex = 1
    var pageSize = 10

    var intent: T? = null

    var uiState = MutableLiveData<UiState>(UiState.Loading())

    abstract fun onTriggerEvent(eventType: T)

    protected fun analiseState(loadState: CombinedLoadStates, itemCount: Int) = when (loadState.refresh) {
            is LoadState.Error -> {
                if (itemCount == 0) {
                    (loadState.refresh as LoadState.Error).let {
                        UiState.Error(it.error,it.error.message)
                    }
                } else UiState.Nothing
            }

            is LoadState.NotLoading -> {
                if (itemCount == 0) UiState.Empty() else UiState.Nothing
            }

            is LoadState.Loading -> {
                if (itemCount == 0) UiState.Loading() else UiState.Nothing
            }
        }


    abstract fun isFetchData(): Boolean
}