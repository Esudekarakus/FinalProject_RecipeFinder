package com.example.recipefinder.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(recipes: List<RecipeEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recipe: RecipeEntity)

    @Query("SELECT * FROM recipes")
    fun getAllRecipes(): Flow<List<RecipeEntity>>

    @Query("SELECT * FROM recipes WHERE id = :recipeId LIMIT 1")
    fun getRecipeById(recipeId: Int): Flow<RecipeEntity?>


    @Delete(entity = RecipeEntity::class)
    fun delete(entity: RecipeEntity)



}
