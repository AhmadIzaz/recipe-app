package com.example.recipeapp.network

import com.google.gson.annotations.SerializedName

data class ErrorMessage(
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("id")
    val id: String?,
    @SerializedName("illustration")
    val illustration: String?
)