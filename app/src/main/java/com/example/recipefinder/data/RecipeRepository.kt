package com.example.recipefinder.data


import com.example.recipefinder.data.source.local.RecipeEntity
import com.example.recipefinder.data.source.network.RecipeResponse
import com.example.recipefinder.utils.ApiResult
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    suspend fun getRepices(): Flow<ApiResult<RecipeResponse>>
    suspend fun getRecipesSteam():Flow<List<RecipeEntity>>
}