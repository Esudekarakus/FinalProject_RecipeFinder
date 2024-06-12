package com.example.recipefinder.feature.home

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefinder.data.source.DataStoreManager
import com.example.recipefinder.data.source.RecipeRepository
import com.example.recipefinder.data.source.toEntity
import com.example.recipefinder.data.source.toModel
import com.example.recipefinder.models.RecipeModel
import com.example.recipefinder.utils.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject



data class HomeState(
    val isLoading: Boolean = false,
    val randomRecipe: RecipeModel? = null,
    val breakfastRecipes: List<RecipeModel> = emptyList(),
    val dessertRecipes: List<RecipeModel> = emptyList(),
    val mainCourseRecipes: List<RecipeModel> = emptyList(),
    val veganRecipes: List<RecipeModel> = emptyList(),
    val isError: Boolean = false

)

@HiltViewModel
class HomeVM @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val savedStateHandle: SavedStateHandle,

    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeState())
    val uiState: StateFlow<HomeState> = _uiState

    init {
        getRandomRecipe()
        getBreakfastRecipes()
        getDessertRecipes()
        getMainCourseRecipes()
        getVeganRecipes()
    }

    fun getRandomRecipe() {
        viewModelScope.launch {
            _uiState.update { state -> state.copy(isLoading = true) }
            recipeRepository.getRepices().collect { result ->
                when (result) {
                    is ApiResult.Success -> {
                        val recipe = result.data?.recipes?.firstOrNull()?.toModel()
                        _uiState.update { state -> state.copy(randomRecipe = recipe, isLoading = false) }
                    }
                    is ApiResult.Error -> {
                        _uiState.update { state -> state.copy(isError = true, isLoading = false) }
                    }
                    is ApiResult.Loading -> {
                        _uiState.update { state -> state.copy(isLoading = true) }
                    }
                }
            }
        }
    }

    fun getBreakfastRecipes() {
        viewModelScope.launch {
            _uiState.update { state -> state.copy(isLoading = true) }
            recipeRepository.getBreakfastRecipes().collect { result ->
                when (result) {
                    is ApiResult.Success -> {
                        val recipes = result.data?.results?.map { it.toModel() } ?: emptyList()
                        _uiState.update { state -> state.copy(breakfastRecipes = recipes, isLoading = false) }
                    }
                    is ApiResult.Error -> {
                        _uiState.update { state -> state.copy(isError = true, isLoading = false) }
                    }
                    is ApiResult.Loading -> {
                        _uiState.update { state -> state.copy(isLoading = true) }
                    }
                }
            }
        }
    }

    fun getDessertRecipes() {
        viewModelScope.launch {
            _uiState.update { state -> state.copy(isLoading = true) }
            recipeRepository.getDessertRecipes().collect { result ->
                when (result) {
                    is ApiResult.Success -> {
                        val recipes = result.data?.results?.map { it.toModel() } ?: emptyList()
                        _uiState.update { state -> state.copy(dessertRecipes = recipes, isLoading = false) }
                    }
                    is ApiResult.Error -> {
                        _uiState.update { state -> state.copy(isError = true, isLoading = false) }
                    }
                    is ApiResult.Loading -> {
                        _uiState.update { state -> state.copy(isLoading = true) }
                    }
                }
            }
        }
    }

    fun getMainCourseRecipes() {
        viewModelScope.launch {
            _uiState.update { state -> state.copy(isLoading = true) }
            recipeRepository.getMainCourseRecipes().collect { result ->
                when (result) {
                    is ApiResult.Success -> {
                        val recipes = result.data?.results?.map { it.toModel() } ?: emptyList()
                        _uiState.update { state -> state.copy(mainCourseRecipes = recipes, isLoading = false) }
                    }
                    is ApiResult.Error -> {
                        _uiState.update { state -> state.copy(isError = true, isLoading = false) }
                    }
                    is ApiResult.Loading -> {
                        _uiState.update { state -> state.copy(isLoading = true) }
                    }
                }
            }
        }
    }

    fun getVeganRecipes() {
        viewModelScope.launch {
            _uiState.update { state -> state.copy(isLoading = true) }
            recipeRepository.getVeganRecipes().collect { result ->
                when (result) {
                    is ApiResult.Success -> {
                        val recipes = result.data?.results?.map { it.toModel() } ?: emptyList()
                        _uiState.update { state -> state.copy(veganRecipes = recipes, isLoading = false) }
                    }
                    is ApiResult.Error -> {
                        _uiState.update { state -> state.copy(isError = true, isLoading = false) }
                    }
                    is ApiResult.Loading -> {
                        _uiState.update { state -> state.copy(isLoading = true) }
                    }
                }
            }
        }
    }

    fun toggleFavorite(recipe: RecipeModel, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            if (!recipe.isFavorite) {
                callback(true)
                withContext(Dispatchers.IO) {
                    recipe.isFavorite = true
                }
                dataStoreManager.saveFavoriteRecipe(recipe.id)
                recipeRepository.insertRecipe(recipe.toEntity())

            } else {
                // Favori gibi davranıyoruz
                callback(false)
                withContext(Dispatchers.IO) {
                    recipe.isFavorite = false
                }
                dataStoreManager.deleteFavoriteRecipe(recipe.id)
                recipeRepository.deleteRecipe(recipe.toEntity())
            }
        }
    }

    fun checkIfFavorite(recipeId: Int, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            val isFavorite = dataStoreManager.isFavorite(recipeId)
            callback(isFavorite)
        }
    }
}

