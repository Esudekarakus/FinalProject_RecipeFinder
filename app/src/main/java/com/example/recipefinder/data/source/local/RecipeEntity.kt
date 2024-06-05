package com.example.recipefinder.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class RecipeEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val imageUrl: String,
    val ingredients: String,  // Converters kullanarak List<String> türüne dönüştürülecek
    val instructions: String
)
