package com.example.recipefinder.data.source

import com.example.recipefinder.data.source.local.RecipeEntity
import com.example.recipefinder.data.source.network.RecipeResponse
import com.example.recipefinder.data.source.network.RecipeResult

fun RecipeResult.toEntity(): RecipeEntity{
    return RecipeEntity(
        id = this.id,
        title = title,
        instructions = this.instructions,
        imageUrl =this.imageUrl,
        ingredients = ingredients,
    )
}
fun RecipeResponse.toEntity(): List<RecipeEntity>{
    return results.map {
        it?.toEntity() ?: RecipeEntity(
            0,
            "",
            "",
            "",
            "")

    }.orEmpty()

}