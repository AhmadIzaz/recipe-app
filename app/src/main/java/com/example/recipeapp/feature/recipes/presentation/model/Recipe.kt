package com.example.recipeapp.feature.recipes.presentation.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe")
data class Recipe(
    @PrimaryKey val id: String,
    val thumbnail: String?,
    val name: String?,
    val details: String?,
    var isFavourite: Boolean?,
    var isAlcoholic: Boolean?,
)