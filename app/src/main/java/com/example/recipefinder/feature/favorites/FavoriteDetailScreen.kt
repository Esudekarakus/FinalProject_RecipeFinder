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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter

@Composable
fun FavoriteDetailScreen(
    recipeId: Int,
    viewModel: FavoriteDetailVM = hiltViewModel()
){
    val uiState = viewModel.uiState.collectAsState()

    LaunchedEffect(recipeId) {
        viewModel.getByIdSteam(recipeId)
    }

    FavoriteDetailContent(uiState.value)
}

@Composable
fun FavoriteDetailContent(state: FavoriteDetailState) {
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
        else -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Recipe not found")
            }
        }
    }
}




