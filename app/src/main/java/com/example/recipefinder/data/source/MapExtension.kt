package com.example.recipefinder.data.source

import android.text.Spanned
import androidx.core.text.HtmlCompat
import com.example.recipefinder.data.source.local.Converters
import com.example.recipefinder.data.source.local.RecipeEntity
import com.example.recipefinder.data.source.network.Recipe
import com.example.recipefinder.data.source.network.RecipeComplexResult
import com.example.recipefinder.data.source.network.RecipeResponse

import com.example.recipefinder.models.RecipeModel
import com.example.recipefinder.utils.ApiResult


fun Recipe.toEntity(): RecipeEntity {
    return RecipeEntity(
        id = this.id,
        title = title,
        instructions = this.instructions,
        image = this.image,
        isFavorite = false,
        vegetarian = this.vegetarian,
        vegan = this.vegan,
        glutenFree = this.glutenFree,
        dairyFree = this.dairyFree,
        veryHealthy = this.veryHealthy,
        cheap = this.cheap,
        veryPopular = this.veryPopular,
        sustainable = this.sustainable,
        lowFodmap = this.lowFodmap,
        weightWatcherSmartPoints = this.weightWatcherSmartPoints,
        gaps = this.gaps,
        preparationMinutes = this.preparationMinutes,
        cookingMinutes = this.cookingMinutes,
        aggregateLikes = this.aggregateLikes,
        healthScore = this.healthScore,
        creditsText = this.creditsText,
        sourceName = this.sourceName,
        pricePerServing = this.pricePerServing,
        readyInMinutes = this.readyInMinutes,
        servings = this.servings,
        sourceUrl = this.sourceUrl,
        imageType = this.imageType,
        summary = this.summary,
        cuisines = this.cuisines,
        dishTypes = this.dishTypes,
        diets = this.diets,
        occasions = this.occasions,
        spoonacularSourceUrl = this.spoonacularSourceUrl,
        spoonacularScore = this.spoonacularScore,
        originalId = this.originalId,

    )
}
fun RecipeResponse.toEntity(): List<RecipeEntity> {
    return this.recipes.map { it.toEntity() }
}
fun RecipeEntity.toModel(): RecipeModel {
    return RecipeModel(
        id = this.id,
        title = this.title,
        image=this.image,
        summary = this.summary ?: "No summary available",
        isFavorite = this.isFavorite,
        healthScore= this.healthScore,
        aggregateLikes = this.aggregateLikes,

        instructions = this.instructions,
        cookingMinutes = this.cookingMinutes,

    )
}
fun Recipe.toModel(): RecipeModel {
    return RecipeModel(
        id = this.id,
        title = this.title,
        summary = this.summary ?: "No summary available",
        image = this.image,

        healthScore= this.healthScore,
        aggregateLikes = this.aggregateLikes,

        instructions = this.instructions,
        cookingMinutes = this.cookingMinutes,
    )
}
fun ApiResult<RecipeResponse>.toModel(): RecipeModel? {
    return when (this) {
        is ApiResult.Success -> {
            val recipeResponse = this.data
            if (recipeResponse != null && recipeResponse.recipes.isNotEmpty()) {
                recipeResponse.recipes.first().toModel()
            } else {
                null
            }
        }
        is ApiResult.Error -> null
        is ApiResult.Loading -> null
    }
}
fun ApiResult<Recipe>.toRecipeModel(): RecipeModel? {
    return when (this) {
        is ApiResult.Success -> this.data?.toModel()
        is ApiResult.Error -> null
        is ApiResult.Loading -> null
    }
}
fun RecipeComplexResult.toModel(): RecipeModel {
    return RecipeModel(
        id = this.id,
        title = this.title,
        image = this.image,
        summary = "No summary available",
        isFavorite = false,
        healthScore= 0,
        aggregateLikes = 0,

        instructions = null,
        cookingMinutes = 0,
    )
}
fun RecipeModel.toEntity(): RecipeEntity {
    return RecipeEntity(
        id = this.id,
        title = this.title,
        image = this.image,
        summary = this.summary,
        servings = 0,
        preparationMinutes = 0,
        cookingMinutes = this.cookingMinutes,
        aggregateLikes = this.aggregateLikes,
        healthScore = this.healthScore,
        creditsText = null,
        sourceName = null,
        pricePerServing = 0.0,
        readyInMinutes = 0,
        sourceUrl = null,
        imageType = null,
        cuisines = emptyList(),
        dishTypes = emptyList(),
        diets = emptyList(),
        occasions = emptyList(),
        instructions = this.instructions,
        spoonacularScore = 0.0,
        cheap = false,
        veryHealthy = false,
        veryPopular = false,
        sustainable = false,
        lowFodmap = false,
        weightWatcherSmartPoints = 0,
        dairyFree = false,
        glutenFree = false,
        gaps = null,
        vegetarian = false,
        vegan = false,
        originalId = null,
        spoonacularSourceUrl = null,
        isFavorite = this.isFavorite
        )
}
fun String.fromHtml(): Spanned {
    return HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_LEGACY)
}