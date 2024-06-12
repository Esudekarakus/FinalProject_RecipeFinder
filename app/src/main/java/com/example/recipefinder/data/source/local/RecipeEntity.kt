package com.example.recipefinder.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class RecipeEntity(
    @PrimaryKey
    val id: Int,
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

    val originalId: Int?,
    val spoonacularScore: Double,
    val spoonacularSourceUrl: String?,
    var isFavorite: Boolean
)

data class AnalyzedInstructionEntity(
    val name: String?,
    val steps: List<StepEntity>?
)

data class StepEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val number: Int,
    val step: String,
)


