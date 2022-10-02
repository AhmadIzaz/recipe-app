package com.example.recipeapp.feature.recipes.data.model

import com.google.gson.annotations.SerializedName

class RecipeResponse {
    @SerializedName("drinks")
    var drinks: ArrayList<Drinks> = arrayListOf()
}