package com.example.recipeapp.common

import android.content.SharedPreferences
import com.example.recipeapp.feature.recipes.ui.RecipesFragment

class SharePrefHelper(private val sharedPreferences: SharedPreferences) {

    companion object {

        private const val SEARCH_BY_STATE_KEY = "SEARCH_BY_STATE_KEY"
    }

    fun storeSearchByState(stateIndex: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(SEARCH_BY_STATE_KEY, stateIndex)
        editor.apply()
    }

    fun getSearchByState(): RecipesFragment.SearchBy {
        val index =
            sharedPreferences.getInt(SEARCH_BY_STATE_KEY, RecipesFragment.SearchBy.BY_NAME.ordinal)
        return if (index == 0) RecipesFragment.SearchBy.BY_NAME else RecipesFragment.SearchBy.BY_ALPHABET
    }

}