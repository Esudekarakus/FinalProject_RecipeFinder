package com.example.recipefinder.data.source.network

import com.google.gson.annotations.SerializedName

data class RecipeComplexResult(
    @SerializedName("id")
    val id:Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("image")
    val image: String,

)