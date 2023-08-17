package com.sis.base.utils

sealed class UiState {
    object Nothing : UiState()
    data class Error(var ex: Throwable? = null, val message: String? = null) : UiState()
    data class Loading(var message: String? = null) : UiState()
    data class Empty(var message: String? = null) : UiState();

}