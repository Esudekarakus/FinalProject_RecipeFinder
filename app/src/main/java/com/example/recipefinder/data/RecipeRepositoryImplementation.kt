package com.example.recipefinder.data

import com.example.fiveweekproject.utils.ApiResult
import com.example.recipefinder.data.source.local.RecipeDao
import com.example.recipefinder.data.source.network.NetworkDataSource
import com.example.recipefinder.data.source.network.RecipeResponse
import com.example.recipefinder.data.source.toEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecipeRepositoryImplementation @Inject constructor(
    private val localDtaSource: RecipeDao,
    private val networkDataSource: NetworkDataSource
) : RecipeRepository {
    override suspend fun getRepices(): Flow<ApiResult<RecipeResponse>> {
        val recipes = networkDataSource.getRecipes()
        recipes.collect { value ->
            when (value) {
                is ApiResult.Success -> {
                    localDtaSource.insertAll(value.data?.toEntity().orEmpty())

                }

                else -> {

                }
            }
        }
        return recipes
    }
}
