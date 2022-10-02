package com.example.recipeapp.network

import com.google.gson.annotations.SerializedName

data class GenericError(@SerializedName("error") val error: ErrorResponse? = null)