package com.example.recipeapp.feature.favouriterecipe.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipeapp.adapter.RecipeAdapter
import com.example.recipeapp.bases.BaseFragment
import com.example.recipeapp.databinding.FragmentFavouriteRecipeBinding
import com.example.recipeapp.feature.favouriterecipe.presentation.FavouriteRecipesViewModel
import com.example.recipeapp.feature.recipes.presentation.model.Recipe
import org.koin.android.ext.android.inject

class FavouriteRecipeFragment : BaseFragment(), RecipeAdapter.OnClickListener {

    private var _binding: FragmentFavouriteRecipeBinding? = null
    private val binding get() = _binding!!

    private val favouriteRecipesViewModel by inject<FavouriteRecipesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouriteRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupObservers()
    }


    private fun setupObservers() {
        favouriteRecipesViewModel.getFavouriteRecipe().observe(viewLifecycleOwner) { list ->
            val recipeAdapter = context?.let { context ->
                RecipeAdapter(
                    context,
                    list,
                    this,
                    FavouriteRecipeFragment::class.java.simpleName
                )
            }
            binding.rvRecipes.layoutManager =
                LinearLayoutManager(context)
            binding.rvRecipes.adapter = recipeAdapter
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            FavouriteRecipeFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onFavouriteClick(recipe: Recipe) {

    }
}