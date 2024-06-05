package com.example.recipefinder.feature.RecipeScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.unit.dp
import com.example.recipefinder.models.RecipeModel
import java.lang.reflect.Modifier


@Composable
fun RecipeListScreen(recipeList: List<RecipeModel>, onItemClick: (RecipeModel) -> Unit) {
    LazyColumn {
        items(recipeList) { recipe ->
            RecipeItem(recipe = recipe, onItemClick = onItemClick)
        }
    }
}

@Composable
fun RecipeItem(recipe: RecipeModel, onItemClick: (RecipeModel) -> Unit) {
    Column(
        modifier = androidx.compose.ui.Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onItemClick(recipe) }
    ) {
        Text(
            text = recipe.title,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = androidx.compose.ui.Modifier.height(8.dp))

    }
}
