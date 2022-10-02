package com.example.recipeapp.common

import com.example.recipeapp.network.ErrorResponse
import com.example.recipeapp.network.ErrorResult

enum class State {
    DONE, LOADING, ERROR
}

sealed class FetchState {
    data class Success<T>(val data: T?) : FetchState()
    data class Error(val error: ErrorResult) : FetchState()
    data class ErrorApi(val error: ErrorResponse) : FetchState()
    data class Loading(val show: Boolean = true) : FetchState()
}