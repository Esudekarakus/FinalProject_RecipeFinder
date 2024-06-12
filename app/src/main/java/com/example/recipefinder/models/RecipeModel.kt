package com.example.recipefinder.models

import android.text.Spanned
import com.example.recipefinder.data.source.fromHtml

data class RecipeModel(
    val id: Int,
    val title: String,
    val image: String?,
    val summary: String,
    var isFavorite: Boolean = false,
    val cookingMinutes: Int?,
    val aggregateLikes: Int,
    val healthScore: Int,
    val instructions: String?
) {
    val plainSummary: String
        get() = summary.fromHtml().toString()

    val plainInstructions: String?
        get() = instructions?.fromHtml()?.toString()
}
