package com.example.recipeapp

import com.example.recipeapp.bases.BaseViewModel
import com.example.recipeapp.feature.recipes.presentation.model.Recipe

class MainViewModel(
    private val mainRepository: MainRepositoryImpl
) : BaseViewModel() {

    fun getFavouriteRecipe(): List<Recipe> {
        return mainRepository.getFavouriteRecipe()
    }
}