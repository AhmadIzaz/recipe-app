package com.example.recipeapp.feature.recipes.presentation

import com.example.recipeapp.bases.BaseViewModel
import com.example.recipeapp.common.FetchState
import com.example.recipeapp.common.SingleLiveEvent
import com.example.recipeapp.extensions.*
import com.example.recipeapp.feature.recipes.data.RecipeTransformer
import com.example.recipeapp.feature.recipes.data.RecipesRepositoryImpl
import com.example.recipeapp.feature.recipes.presentation.model.Recipe

class RecipesViewModel(
    private val recipeRepository: RecipesRepositoryImpl,
    private val recipeTransformer: RecipeTransformer
) : BaseViewModel() {

    private val searchRecipeState = SingleLiveEvent<FetchState>()

    fun searchRecipeByName(searchText: String) {
        searchRecipeState.postValue(FetchState.Loading())
        ioJob {
            doNetworkCall { recipeRepository.searchRecipesByName(searchText) }
                .awaitForResult()
                .onSuccessResult {
                    searchRecipeState.postValue(
                        FetchState.Success(
                            recipeTransformer.transformRecipeResponseToRecipeModel(
                                it
                            )
                        )
                    )
                }.onFailureWithErrorResponse {
                    searchRecipeState.postValue(FetchState.ErrorApi(it))
                }
        }
    }

    fun searchRecipeByFirstAlphabet(searchText: String) {
        searchRecipeState.postValue(FetchState.Loading())
        ioJob {
            doNetworkCall { recipeRepository.searchRecipesByAlphabet(searchText) }
                .awaitForResult()
                .onSuccessResult {
                    searchRecipeState.postValue(
                        FetchState.Success(
                            recipeTransformer.transformRecipeResponseToRecipeModel(
                                it
                            )
                        )
                    )
                }.onFailureWithErrorResponse {
                    searchRecipeState.postValue(FetchState.ErrorApi(it))
                }
        }
    }

    fun getSearchRecipeState(): SingleLiveEvent<FetchState> {
        return searchRecipeState
    }

    fun storeFavouriteRecipe(recipe: Recipe) {
        ioJob {
            recipeRepository.storeFavouriteRecipe(recipe)
        }
    }


}