package com.example.recipeapp.room

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.recipeapp.feature.recipes.presentation.model.Recipe

@Dao
interface RecipeDao {

    @Insert(onConflict = REPLACE)
    suspend fun insert(recipe: Recipe)

    @Update
    fun update(recipe: Recipe)

    @Delete
    fun delete(recipe: Recipe)

    @Query("DELETE FROM recipe")
    fun deleteAllRecipes()

    @get:Query("SELECT * FROM recipe")
    val allFavouriteRecipes: List<Recipe>

    @get:Query("SELECT * FROM recipe")
    val allFavouriteDrinkRecipes: LiveData<List<Recipe>>
}