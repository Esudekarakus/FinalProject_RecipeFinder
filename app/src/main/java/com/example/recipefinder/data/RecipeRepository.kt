package com.example.recipefinder.data

import com.example.fiveweekproject.utils.ApiResult
import com.example.recipefinder.data.source.network.RecipeResponse
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    suspend fun getRepices(): Flow<ApiResult<RecipeResponse>>
}