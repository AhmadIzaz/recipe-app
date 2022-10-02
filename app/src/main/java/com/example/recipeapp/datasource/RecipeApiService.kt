package com.example.recipeapp.datasource

import com.example.recipeapp.feature.recipes.data.model.RecipeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApiService {

    @GET("search.php")
    suspend fun searchByName(@Query("s") keyword: String): Response<RecipeResponse>

    @GET("search.php")
    suspend fun searchByFirstAlphabet(@Query("f") keyword: String): Response<RecipeResponse>

}