package com.example.recipefinder.feature.favorites

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter

@Composable
fun FavoriteDetailScreen(
    recipeId: Int,
    viewModel: FavoriteDetailVM = hiltViewModel(),
    navController: NavController
) {
    val uiState = viewModel.uiState.collectAsState()

    LaunchedEffect(recipeId) {
        viewModel.getByIdSteam(recipeId)
    }

    FavoriteDetailContent(uiState.value, navController, viewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteDetailContent(state: FavoriteDetailState, navController: NavController, viewModel: FavoriteDetailVM) {
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
                        colors = TopAppBarDefaults.topAppBarColors(Color(0xFFFF7F50)),
                        navigationIcon = {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                            }
                        },
                        actions = {
                            IconButton(onClick = {
                                viewModel.toggleFavorite(recipe) { isFavorite ->
                                    val iconTint = if (isFavorite) Color.Red else Color.Gray
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
                content = { padding ->
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
                        } ?: run {
                            Spacer(modifier = Modifier.height(200.dp))
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = recipe.title,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        Text(
                            text = recipe.plainSummary ?: "Summary not available",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        Text(
                            text = "Instructions",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )

                        Text(
                            text = recipe.plainInstructions?: "Instructions not available",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        Text(
                            text = "Health Score: ${recipe.healthScore}",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )

                        Text(
                            text = "Cooking Time: ${recipe.cookingMinutes ?: "Not Given"} minutes",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )

                        Text(
                            text = "Likes: ${recipe.aggregateLikes}",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(vertical = 8.dp)
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

