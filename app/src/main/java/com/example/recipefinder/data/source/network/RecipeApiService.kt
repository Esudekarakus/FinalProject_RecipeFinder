package com.example.recipefinder.data.source.network

import com.example.recipefinder.models.RecipeModel
import retrofit2.Response
import retrofit2.http.GET

interface RecipeApiService {
    @GET("recipes")
    suspend fun getRecipes(): Response<RecipeResponse>
}
