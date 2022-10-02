package com.example.recipeapp.feature.recipes.data

import com.example.recipeapp.feature.recipes.data.model.RecipeResponse
import com.example.recipeapp.feature.recipes.presentation.model.Recipe

interface RecipeTransformer {
    fun transformRecipeResponseToRecipeModel(recipeResponse: RecipeResponse?): List<Recipe>?
}

class RecipeTransformerImpl() : RecipeTransformer {
    override fun transformRecipeResponseToRecipeModel(recipeResponse: RecipeResponse?): List<Recipe>? {
        return recipeResponse?.drinks?.map { recipe ->
            Recipe(
                recipe.idDrink.toString(),
                recipe.strDrinkThumb,
                recipe.strDrink,
                recipe.strInstructions,
                false,
                false
            )
        }
    }
}