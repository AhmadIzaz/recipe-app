package com.example.recipeapp.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.recipeapp.R
import com.example.recipeapp.databinding.ActivityMainBinding
import com.example.recipeapp.feature.favouriterecipe.ui.FavouriteRecipeFragment
import com.example.recipeapp.feature.recipes.ui.RecipesFragment


class MainActivity : AppCompatActivity() {

    private lateinit var favouriteRecipeFragment: FavouriteRecipeFragment
    private lateinit var recipeFragment: RecipesFragment
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.drinks_recipes)
        recipeFragment = RecipesFragment.newInstance()
        favouriteRecipeFragment = FavouriteRecipeFragment.newInstance()

        replaceFragment(recipeFragment)

        setupListeners()
    }

    private fun setupListeners() {
        binding.navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    supportActionBar?.title = getString(R.string.drinks_recipes)
                    replaceFragment(recipeFragment)
                }
                R.id.navigation_favourite -> {
                    supportActionBar?.title = getString(R.string.favourites_recipes)
                    replaceFragment(favouriteRecipeFragment)
                }
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment, fragment.javaClass.simpleName).commit()
    }

}