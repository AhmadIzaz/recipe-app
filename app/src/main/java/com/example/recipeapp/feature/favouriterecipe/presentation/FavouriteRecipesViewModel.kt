package com.example.recipeapp.feature.favouriterecipe.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipeapp.bases.BaseViewModel
import com.example.recipeapp.feature.favouriterecipe.data.FavouriteRecipesRepositoryImpl
import com.example.recipeapp.feature.recipes.presentation.model.Recipe

class FavouriteRecipesViewModel(
    private val recipeRepository: FavouriteRecipesRepositoryImpl
) : BaseViewModel() {

    fun getFavouriteRecipe(): LiveData<List<Recipe>> {
        return recipeRepository.getFavouriteRecipe()
    }


}