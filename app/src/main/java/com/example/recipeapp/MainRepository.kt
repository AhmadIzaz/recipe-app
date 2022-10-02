package com.example.recipeapp

import androidx.lifecycle.LiveData
import com.example.recipeapp.feature.recipes.presentation.model.Recipe
import com.example.recipeapp.room.RecipeDatabase

interface MainRepository {
    fun getFavouriteRecipe(): List<Recipe>
}

class MainRepositoryImpl(
    private val recipeDatabase: RecipeDatabase
) : MainRepository {

    override fun getFavouriteRecipe(): List<Recipe> {
        return recipeDatabase.recipeDao().allFavouriteRecipes
    }

}