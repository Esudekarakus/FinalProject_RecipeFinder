package com.example.recipefinder.data.source.network

import com.google.gson.annotations.SerializedName

data class RecipeResponse(
    @SerializedName("info")
    val info: String,
    @SerializedName("results")
    val results: List<RecipeResult?>
)
