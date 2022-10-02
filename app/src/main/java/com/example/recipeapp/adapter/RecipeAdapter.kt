package com.example.recipeapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.R
import com.example.recipeapp.databinding.RecipeItemLayoutBinding
import com.example.recipeapp.feature.recipes.presentation.model.Recipe
import com.example.recipeapp.feature.recipes.ui.RecipesFragment

class RecipeAdapter(
    private val context: Context,
    private val recipesList: List<Recipe>,
    private val onClickListener: OnClickListener,
    private val comingFrom: String
) : RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: RecipeItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecipeItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = recipesList[position]
        holder.binding.nameTextview.text = recipe.name
        holder.binding.detailsTextview.text = recipe.details
        Glide.with(context).load(recipe.thumbnail)
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.binding.drinkThumbnailImageview)

        holder.binding.alcoholicCheckbox.isChecked = recipe.isAlcoholic == true

        if (recipe.isFavourite == true)
            holder.binding.favouriteImageView.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_star
                )
            )
        else
            holder.binding.favouriteImageView.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_star_border
                )
            )

        if (comingFrom == RecipesFragment::class.java.simpleName) {
            holder.binding.alcoholicCheckbox.isEnabled = true
            holder.binding.favouriteImageView.isEnabled = true
            holder.binding.alcoholicCheckbox.setOnCheckedChangeListener { view, isChecked ->
                recipe.isAlcoholic = isChecked
            }

            holder.binding.favouriteImageView.setOnClickListener {
                recipe.isFavourite = true
                onClickListener.onFavouriteClick(recipe)
                notifyItemChanged(position)
            }
        } else {
            holder.binding.alcoholicCheckbox.isEnabled = false
            holder.binding.favouriteImageView.isEnabled = false
            holder.binding.alcoholicCheckbox.setOnCheckedChangeListener(null)
            holder.binding.favouriteImageView.setOnClickListener(null)
        }
    }

    override fun getItemCount(): Int = recipesList.size

    interface OnClickListener {
        fun onFavouriteClick(recipe: Recipe)
    }
}