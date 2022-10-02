package com.example.recipeapp.extensions

import androidx.lifecycle.viewModelScope
import com.example.recipeapp.bases.BaseViewModel
import com.example.recipeapp.network.ApiResult
import com.example.recipeapp.network.ErrorResponse
import com.example.recipeapp.network.GenericError
import com.google.gson.Gson
import kotlinx.coroutines.*
import retrofit2.Response
import java.net.UnknownHostException
import kotlin.coroutines.cancellation.CancellationException


suspend fun <R> BaseViewModel.doNetworkCall(block: suspend () -> Response<R>): Deferred<Response<R>> {
    return viewModelScope.async(coroutineContextProvider.IO) { block.invoke() }
}

suspend fun <R> Deferred<Response<R>>.awaitForResult(): ApiResult<R> {
    val result = ApiResult<R>()
    try {
        val response = await()
        result.response = response
        result.data = response.body()
    } catch (ex: Exception) {
        result.exception = ex
    } finally {
        return result
    }
}

fun <T> Response<T>.errorResponse(): ErrorResponse? {
    val stringError = errorBody()?.string()
    if (stringError.isNullOrEmpty()) return null
    return try {
        val errorResult = Gson().fromJson(stringError, GenericError::class.java)
        errorResult.error?.let {
            ErrorResponse(
                it.code,
                it.details,
                it.message,
                it.isBlocked,
                it.requestMade,
                it.requestsLeft,
                it.timeLeft,
                it.totalRequests
            )
        }
    } catch (exception: Exception) {
        ErrorResponse(
            code(),
            "Some error occurred. Please try again",
            "Some error occurred. Please try again"
        )
    }
}

fun <R> ApiResult<R>.onSuccessResult(block: (R) -> Unit): ApiResult<R> {
    if (this.isSuccess) {
        response?.body()?.notNull { block(it) }
    }
    return this
}

fun <R> ApiResult<R>.onFailureWithErrorResponse(block: (ErrorResponse) -> Unit) {
    if (!this.isSuccess || this.response?.code() != 200) {
        if (exception is CancellationException) return
        exception.notNull {
            if (exception is UnknownHostException) {
                val errorMessage =
                    "Please check your internet connection"
                val errorCode = response?.code() ?: 0
                block(ErrorResponse(errorCode, errorMessage, errorMessage))
            } else {
                val errorMessage = it.localizedMessage ?: "Some error occurred. Please try again"
                val errorCode = response?.code() ?: 0
                block(ErrorResponse(errorCode, errorMessage, errorMessage))
            }
        }
        exception.isNull {
            val errorMessage = "[${response?.code()}] Some error occurred. Please try again"
            val errorCode = response?.code() ?: 0
            block(ErrorResponse(errorCode, errorMessage, errorMessage))
        }
    } /*else {
        response?.errorResponse().notNull {
            block(it)
        }
    }*/
}

fun BaseViewModel.ioJob(block: suspend CoroutineScope.() -> Unit): Job {
    return viewModelScope.launch(coroutineContextProvider.IO) {
        block()
    }
}