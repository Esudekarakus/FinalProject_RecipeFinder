package com.example.recipefinder.data.source.network

import com.google.gson.annotations.SerializedName

data class RecipeResult(
    @SerializedName("id")
    val id:Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("image")
    val imageUrl: String,
    @SerializedName("ingredients")
    val ingredients: String,  // Converters kullanarak List<String> türüne dönüştürülecek
    @SerializedName("instructions")
    val instructions: String
)