package com.example.recipefinder.data.source.network

import com.google.gson.annotations.SerializedName

data class RecipeResponse(
    val recipes: List<Recipe>
)

data class Recipe(
    val vegetarian: Boolean,
    val vegan: Boolean,
    val glutenFree: Boolean,
    val dairyFree: Boolean,
    val veryHealthy: Boolean,
    val cheap: Boolean,
    val veryPopular: Boolean,
    val sustainable: Boolean,
    val lowFodmap: Boolean,
    val weightWatcherSmartPoints: Int,
    val gaps: String?,
    val preparationMinutes: Int?,
    val cookingMinutes: Int?,
    val aggregateLikes: Int,
    val healthScore: Int,
    val creditsText: String?,
    val sourceName: String?,
    val pricePerServing: Double,
    val extendedIngredients: List<ExtendedIngredient>,
    val id: Int,
    val title: String,
    val readyInMinutes: Int,
    val servings: Int,
    val sourceUrl: String?,
    val image: String?,
    val imageType: String?,
    val summary: String?,
    val cuisines: List<String>,
    val dishTypes: List<String>,
    val diets: List<String>,
    val occasions: List<String>,
    val instructions: String?,
    val analyzedInstructions: List<AnalyzedInstruction>?,
    val originalId: Int?,
    val spoonacularScore: Double,
    val spoonacularSourceUrl: String?
)

data class ExtendedIngredient(
    val id: Int,
    val aisle: String,
    val image: String?,
    val consistency: String,
    val name: String,
    val nameClean: String?,
    val original: String,
    val originalName: String,
    val amount: Double,
    val unit: String,
    val meta: List<String>,
    val measures: Measures
)

data class Measures(
    val us: Measure,
    val metric: Measure
)

data class Measure(
    val amount: Double,
    val unitShort: String,
    val unitLong: String
)

data class AnalyzedInstruction(
    val name: String?,
    val steps: List<Step>?
)

data class Step(
    val number: Int,
    val step: String,
    val ingredients: List<Ingredient>,
    val equipment: List<Equipment>
)

data class Ingredient(
    val id: Int,
    val name: String,
    val localizedName: String?,
    val image: String?
)

data class Equipment(
    val id: Int,
    val name: String,
    val localizedName: String?,
    val image: String?
)

