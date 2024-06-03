package com.example.recipefinder.models

data class RecipeModel(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val ingredients: List<String>,
    val instructions: String
)