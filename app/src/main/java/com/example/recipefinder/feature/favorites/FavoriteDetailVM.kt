package com.example.recipefinder.feature.favorites

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefinder.data.source.RecipeRepository
import com.example.recipefinder.models.RecipeModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


data class FavoriteDetailState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val recipe: RecipeModel? = null
)
@HiltViewModel
class FavoriteDetailVM @Inject constructor(
    private val repository: RecipeRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow(FavoriteDetailState())
    val uiState: StateFlow<FavoriteDetailState> = _uiState

    fun getByIdSteam(recipeId: Int) {
        viewModelScope.launch {
            _uiState.value = FavoriteDetailState(isLoading = true)
            repository.getRecipeByIdSteam(recipeId).collect { recipeModel ->
                if (recipeModel != null) {
                    _uiState.value = FavoriteDetailState(recipe = recipeModel)
                } else {
                    _uiState.value = FavoriteDetailState(isError = true)
                }
            }
        }
    }
}