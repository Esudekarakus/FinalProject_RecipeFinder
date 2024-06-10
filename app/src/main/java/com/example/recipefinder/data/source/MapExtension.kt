package com.example.recipefinder.data.source

import android.text.Spanned
import androidx.core.text.HtmlCompat
import com.example.recipefinder.data.source.local.Converters
import com.example.recipefinder.data.source.local.RecipeEntity
import com.example.recipefinder.data.source.network.Recipe
import com.example.recipefinder.data.source.network.RecipeResponse
import com.example.recipefinder.data.source.network.RecipeResult
import com.example.recipefinder.models.RecipeModel


fun Recipe.toEntity(): RecipeEntity {
    return RecipeEntity(
        id = this.id,
        title = title,
        instructions = this.instructions,
        image = this.image,

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
        summary = this.summary ?: "No summary available"
    )
}

fun String.fromHtml(): Spanned {
    return HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_LEGACY)
}