package com.example.recipeapp.network

import retrofit2.Response

data class ApiResult<R>(
        var data: R? = null,
        var response: Response<R>? = null,
        var exception: Exception? = null
) {

    val isSuccess: Boolean
        get() = exception == null
}