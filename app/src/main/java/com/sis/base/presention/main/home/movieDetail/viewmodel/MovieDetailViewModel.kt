package com.sis.base.presention.main.home.movieDetail.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sis.base.domain.model.MovieDetailDto
import com.sis.base.domain.repository.MovieDetailRepository
import com.sis.base.presention.base.BaseViewModel
import com.sis.base.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author mojtaba hemmati sis
 */
@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieRepository: MovieDetailRepository
) : BaseViewModel<MovieDetailListIntent>() {
    lateinit var lastEvent:MovieDetailListIntent
    val movieDetail = MutableLiveData<MovieDetailDto>(null)

    override fun onTriggerEvent(eventType: MovieDetailListIntent) {
        lastEvent = eventType
        viewModelScope.launch(Dispatchers.IO) {
            when (eventType) {
                is MovieDetailListIntent.RequestItemDetail -> requestItemDetail(eventType.movieId)
            }
        }
    }

    fun retry(){
        onTriggerEvent(lastEvent)
    }

    private suspend fun requestItemDetail(movieId: String) {
        uiState.postValue(UiState.Loading())
        movieRepository.getMovieDetail(movieId).catch {
            uiState.postValue(UiState.Error(it))
        }.collect {
            uiState.postValue(UiState.Nothing)
            movieDetail.postValue(it)
        }
    }

    override fun isFetchData(): Boolean = movieDetail.value != null
}