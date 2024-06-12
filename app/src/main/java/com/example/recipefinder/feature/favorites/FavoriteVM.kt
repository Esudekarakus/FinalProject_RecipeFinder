package com.example.recipefinder.feature.favorites

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipefinder.data.source.RecipeRepository
import com.example.recipefinder.data.source.local.RecipeEntity
import com.example.recipefinder.models.RecipeModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RecipeState(
    val isLoading: Boolean = false,
    val recipes: List<RecipeModel> = emptyList(),
    val isError: Boolean = false

)

@HiltViewModel
class FavoriteVM @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val savedStateHandle: SavedStateHandle,
): ViewModel() {

    private val _favoriteRecipes = MutableStateFlow<List<RecipeModel>>(emptyList())
    val favoriteRecipes: StateFlow<List<RecipeModel>> = _favoriteRecipes.asStateFlow()

    init {
        getFavoriteRecipes()
    }

    private fun getFavoriteRecipes() {
        viewModelScope.launch {
            recipeRepository.getRecipesSteam()
                .map { entities -> entities.map { it.toRecipeModel() } }
                .collect { recipes ->
                    _favoriteRecipes.value = recipes
                }
        }
    }

    private fun RecipeEntity.toRecipeModel(): RecipeModel {
        return RecipeModel(
            id = this.id,
            title = this.title,
            image = this.image,
            summary = this.summary ?: "No summary available",
        )
    }
}

