package com.example.recipeapp.feature.favouriterecipe.data

import androidx.lifecycle.LiveData
import com.example.recipeapp.feature.recipes.presentation.model.Recipe
import com.example.recipeapp.room.RecipeDatabase

interface FavouriteRecipeRepository {
    fun getFavouriteRecipe(): LiveData<List<Recipe>>
}

class FavouriteRecipesRepositoryImpl(
    private val recipeDatabase: RecipeDatabase
) : FavouriteRecipeRepository {

    override fun getFavouriteRecipe(): LiveData<List<Recipe>> {
        return recipeDatabase.recipeDao().allFavouriteDrinkRecipes
    }

}