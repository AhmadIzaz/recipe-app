package com.example.recipeapp.feature.recipes.data

import com.example.recipeapp.datasource.RecipeApiService
import com.example.recipeapp.feature.recipes.data.model.RecipeResponse
import com.example.recipeapp.feature.recipes.presentation.model.Recipe
import com.example.recipeapp.room.RecipeDatabase
import retrofit2.Response

interface RecipeRepository {
    suspend fun searchRecipesByName(searchText: String): Response<RecipeResponse>
    suspend fun searchRecipesByAlphabet(searchText: String): Response<RecipeResponse>
    suspend fun storeFavouriteRecipe(recipe: Recipe)
}

class RecipesRepositoryImpl(
    private val recipeApiService: RecipeApiService,
    private val recipeDatabase: RecipeDatabase
) : RecipeRepository {
    override suspend fun searchRecipesByName(searchText: String): Response<RecipeResponse> {
        return recipeApiService.searchByName(searchText)
    }

    override suspend fun searchRecipesByAlphabet(searchText: String): Response<RecipeResponse> {
        return recipeApiService.searchByFirstAlphabet(searchText)
    }

    override suspend fun storeFavouriteRecipe(recipe: Recipe) {
        recipeDatabase.recipeDao().insert(recipe)
    }

}