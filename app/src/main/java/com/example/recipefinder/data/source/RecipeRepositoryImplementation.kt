package com.example.recipefinder.data.source

import com.example.recipefinder.data.source.local.RecipeDao
import com.example.recipefinder.data.source.local.RecipeEntity
import com.example.recipefinder.data.source.network.NetworkDataSource
import com.example.recipefinder.data.source.network.RecipeComplexResponse
import com.example.recipefinder.data.source.network.RecipeResponse
import com.example.recipefinder.models.RecipeModel
import com.example.recipefinder.utils.ApiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecipeRepositoryImplementation @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    private val localSource: RecipeDao
): RecipeRepository {
    override suspend fun getRepices(): Flow<ApiResult<RecipeResponse>> {
        val recipeResponse = networkDataSource.getRecipes()
        recipeResponse.collect { value ->
            when (value) {
                is ApiResult.Success -> {
                   withContext(Dispatchers.IO) {
                       localSource.insertAll(value.data?.toEntity().orEmpty())
                   }

                }

                else -> {}
            }
        }
        return recipeResponse
    }

    override suspend fun getRecipesSteam(): Flow<List<RecipeEntity>> {
        return localSource.getAllRecipes()
    }

    override suspend fun getRecipeByIdSteam(recipeId: Int): Flow<RecipeModel?> {
        return localSource.getRecipeById(recipeId).map { entity ->
            entity?.toModel()
        }


    }

    override suspend fun getRecipeById(id: Int): Flow<ApiResult<RecipeResponse>> {
        return networkDataSource.getRecipeById(id)
    }

    override suspend fun getBreakfastRecipes(): Flow<ApiResult<RecipeComplexResponse>> {
        return networkDataSource.getBreakfastRecipes()
    }

    override suspend fun getDessertRecipes(): Flow<ApiResult<RecipeComplexResponse>> {
        return networkDataSource.getDessertRecipes()
    }

    override suspend fun getMainCourseRecipes(): Flow<ApiResult<RecipeComplexResponse>> {
        return networkDataSource.getMainCourseRecipes()
    }

    override suspend fun getVeganRecipes(): Flow<ApiResult<RecipeComplexResponse>> {
        return networkDataSource.getVeganRecipes()
    }


}