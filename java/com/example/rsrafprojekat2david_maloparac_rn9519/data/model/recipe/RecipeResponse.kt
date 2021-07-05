package com.example.rsrafprojekat2david_maloparac_rn9519.data.model.recipe

import com.squareup.moshi.Json

data class RecipeResponse (
        /*@Json(name="id")
        val id: String,*/
        @Json(name="social_rank")
        val social_rank: String,
        @Json(name="ingredients")
        val ingredients: List<String>
)

data class FetchedRecipe(
        @Json(name="recipe")
        val recipe: RecipeResponse
)