package com.example.recipefinder.data.source.network



import com.example.recipefinder.utils.ApiResult
import kotlinx.coroutines.flow.Flow

interface NetworkDataSource {

    suspend fun getRecipes(): Flow<ApiResult<RecipeResponse>>

}