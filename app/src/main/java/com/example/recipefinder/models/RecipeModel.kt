package com.example.recipefinder.models

data class RecipeModel(
    val id: Int,
    val title: String,
    val image: String?,
    val summary: String?
)