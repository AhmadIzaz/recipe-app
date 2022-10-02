package com.example.recipeapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.recipeapp.databinding.ActivityMainBinding
import com.example.recipeapp.feature.favouriterecipe.ui.FavouriteRecipeFragment
import com.example.recipeapp.feature.recipes.ui.RecipesFragment


class MainActivity : AppCompatActivity() {

    private lateinit var favouriteRecipeFragment: FavouriteRecipeFragment
    private lateinit var recipeFragment: RecipesFragment
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!


    /*private val mainViewModel by inject<MainViewModel>()
    private val notificationHelper by inject<NotificationHelper>()
    private val alarmManagerHelper by inject<AlarmManagerHelper>()*/

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

    /*@Subscribe(threadMode = ThreadMode.MAIN)
    fun onDrinkReminderEvent(event: DrinkReminderEvent) {
        Log.d("RECIPEAPP", "unauthorized triggered")
        if (event.rescheduleAlarm) {
//            alarmManagerHelper.scheduleSecondAlarm()
        }
        if (event.notifyUser) {
            val _recipes = mainViewModel.getFavouriteRecipe()*//*.observe(this) { _recipes ->*//*
            Log.d("RECIPEAPP", "$_recipes")
            if (_recipes.isNullOrEmpty()) {
                notificationHelper.showNotification(
                    getString(R.string.feeling_thirsty),
                    getString(R.string.need_some_drink),
                    ""
                )
            } else {
                val _recipe = _recipes[0]
                notificationHelper.showNotification(
                    _recipe.name.toString(),
                    _recipe.details.toString(),
                    _recipe.thumbnail.toString()
                )
            }
        }
    }*/
}