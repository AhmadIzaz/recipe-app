package com.example.recipeapp.feature.recipes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipeapp.R
import com.example.recipeapp.adapter.RecipeAdapter
import com.example.recipeapp.bases.BaseFragment
import com.example.recipeapp.common.FetchState
import com.example.recipeapp.common.SharePrefHelper
import com.example.recipeapp.databinding.FragmentRecipesBinding
import com.example.recipeapp.extensions.cast
import com.example.recipeapp.extensions.isNull
import com.example.recipeapp.extensions.showToast
import com.example.recipeapp.extensions.textColor
import com.example.recipeapp.feature.recipes.presentation.RecipesViewModel
import com.example.recipeapp.feature.recipes.presentation.model.Recipe
import com.example.recipeapp.notifications.AlarmManagerHelper
import org.koin.android.ext.android.inject


class RecipesFragment : BaseFragment(), RecipeAdapter.OnClickListener {

    private var searchBy: SearchBy = SearchBy.BY_NAME
    private var _binding: FragmentRecipesBinding? = null
    private val binding get() = _binding!!

    private val recipesViewModel by inject<RecipesViewModel>()
    private val sharePrefHelper by inject<SharePrefHelper>()
    val alarmManagerHelper by inject<AlarmManagerHelper>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupObservers()
        setupListeners()
        apiCalls()
        scheduleAlarm()
    }

    private fun scheduleAlarm() {
        alarmManagerHelper.scheduleAlarm()
    }

    private fun setupViews() {
        val state = sharePrefHelper.getSearchByState()
        if (state == SearchBy.BY_ALPHABET) {
            binding.byAlphabetRadioBtn.isChecked = true
        } else if (state == SearchBy.BY_NAME) {
            binding.byNameRadioBtn.isChecked = true
        }
    }

    private fun setupListeners() {
        binding.radioGroup.setOnCheckedChangeListener { p0, id ->
            if (id == binding.byNameRadioBtn.id) {
                searchBy = SearchBy.BY_NAME
                context?.let {
                    binding.byNameRadioBtn.textColor(it, R.color.black)
                    binding.byAlphabetRadioBtn.textColor(it, R.color.gray)
                }
            } else if (id == binding.byAlphabetRadioBtn.id) {
                searchBy = SearchBy.BY_ALPHABET
                context?.let {
                    binding.byNameRadioBtn.textColor(it, R.color.gray)
                    binding.byAlphabetRadioBtn.textColor(it, R.color.black)
                }
            }
            sharePrefHelper.storeSearchByState(searchBy.ordinal)

        }

        binding.searchIconImageView.setOnClickListener {
            val keyword = binding.searchEditText.text.toString()
            if (keyword.isEmpty()) {
                activity?.showToast(getString(R.string.can_not_leave_empty_field))
            } else
                searchDrinks(keyword, searchBy)
        }

        binding.searchEditText.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val keyword = binding.searchEditText.text.toString()
                if (keyword.isEmpty()) {
                    activity?.showToast(getString(R.string.can_not_leave_empty_field))
                } else
                    searchDrinks(keyword, searchBy)
                return@OnEditorActionListener true
            }
            false
        })

    }

    private fun setupObservers() {
        recipesViewModel.getSearchRecipeState().observe(viewLifecycleOwner) { searchState ->
            dismissSpinner()
            when (searchState) {
                is FetchState.Success<*> -> {
                    searchState.data.isNull {
                        activity?.showToast(getString(R.string.no_items_available))
                    }
                    searchState.data?.cast<List<Recipe>> { recipeList ->
                        if (recipeList.isEmpty()) {
                            activity?.showToast(getString(R.string.no_items_available))
                        } else
                            handleSuccess(recipeList)
                    }
                }
                is FetchState.ErrorApi -> {
                    activity?.showToast("${searchState.error.getMessage().description}")
                }
                is FetchState.Loading -> {
                    showSpinner()
                }
                else -> {

                }
            }
        }
    }

    private fun handleSuccess(recipes: List<Recipe>) {
        val recipeAdapter = context?.let {
            RecipeAdapter(
                it,
                recipes,
                this,
                RecipesFragment::class.java.simpleName
            )
        }
        binding.rvRecipes.layoutManager =
            LinearLayoutManager(context)
        binding.rvRecipes.adapter = recipeAdapter

    }

    private fun apiCalls() {
        searchDrinks()
    }

    private fun searchDrinks(keyword: String = "margarita", searchBy: SearchBy = SearchBy.BY_NAME) {
        when (searchBy) {
            SearchBy.BY_NAME -> {
                recipesViewModel.searchRecipeByName(keyword)
            }
            SearchBy.BY_ALPHABET -> {
                recipesViewModel.searchRecipeByFirstAlphabet(keyword)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            RecipesFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onFavouriteClick(recipe: Recipe) {
        recipesViewModel.storeFavouriteRecipe(recipe)
    }

    enum class SearchBy {
        BY_NAME, BY_ALPHABET
    }

}