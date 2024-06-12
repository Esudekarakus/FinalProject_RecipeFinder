package com.example.recipefinder.feature.recipe

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter

@Composable
fun RecipeScreen(
    recipeId: Int,
    viewModel: RecipeVM = hiltViewModel(),
    navController: NavController
) {
    val _uiState= viewModel.uiState.collectAsState()

    LaunchedEffect(recipeId) {
        viewModel.getRecipeById(recipeId)
    }

    RecipeContent(_uiState.value, navController, viewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeContent(state: RecipeDetailState , navController: NavController , viewModel: RecipeVM) {
    when {
        state.isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        state.isError -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("An error occurred")
            }
        }

        state.recipe != null -> {
            val recipe = state.recipe
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Recipe Detail") },
                        navigationIcon = {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                            }
                        },
                        actions = {
                            IconButton(onClick = {
                                viewModel.toggleFavorite(recipe) { isFavorite ->
                                    // Favori durumu değiştiğinde yapılacak işlemler burada gerçekleştirilir
                                    // Örneğin, kalp ikonunun rengini değiştirebiliriz
                                    val iconTint = if (isFavorite) Color.Red else Color.Gray
                                    // Kalp ikonunun rengini güncelleme
                                    iconTint.value?.let {
                                        viewModel.uiState.value.recipe?.let { recipe ->
                                            recipe.isFavorite = isFavorite
                                            viewModel.checkIfFavorite(recipe.id) { isFav ->
                                                recipe.isFavorite = isFav
                                            }
                                        }
                                        navController.popBackStack()
                                    }

                                }
                            }) {
                                Icon(
                                    Icons.Filled.Favorite,
                                    contentDescription = "Favorite",
                                    tint = if (recipe.isFavorite) Color.Red else Color.Gray
                                )
                            }
                        }
                    )
                },
                content = { padding->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        recipe.image?.let {
                            Image(
                                painter = rememberImagePainter(it),
                                contentDescription = recipe.title,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                                contentScale = ContentScale.Crop
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = recipe.title,
                            style = MaterialTheme.typography.headlineLarge
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = recipe.plainSummary ?: "No summary available",
                            style = MaterialTheme.typography.bodyLarge
                        )

                    }
                }
            )
        }

        else -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Recipe not found")
            }
        }
    }
}
