package com.example.recipeapp.network

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("details")
    val details: String="",
    @SerializedName("message")
    val message: Any?,
    @SerializedName("isBlocked")
    val isBlocked: Boolean? = false,
    @SerializedName("requestMade")
    val requestMade: String? = "",
    @SerializedName("requestsLeft")
    val requestsLeft: String? = "",
    @SerializedName("timeLeft")
    val timeLeft: String? = "",
    @SerializedName("totalRequests")
    val totalRequests: String? = "",
) {

    fun getMessage(): ErrorMessage =
        try {
            Gson().fromJson(Gson().toJson(message), ErrorMessage::class.java)
        } catch (e: Exception) {
            ErrorMessage("", message.toString(), null, null)
        }
}