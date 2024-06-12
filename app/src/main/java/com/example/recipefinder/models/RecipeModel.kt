package com.example.recipefinder.models

import android.text.Spanned
import com.example.recipefinder.data.source.fromHtml

data class RecipeModel(
    val id: Int,
    val title: String,
    val image: String?,
    val summary: String,
    var isFavorite: Boolean = false
) {
    val plainSummary: String
        get() = summary.fromHtml().toString()
}
