package com.example.rsrafprojekat2david_maloparac_rn9519.data.model.dish

import com.squareup.moshi.Json

data class DishResponse (
    @Json(name="id")
    val id: String,
    @Json(name="title")
    val title: String,
    @Json(name="imageUrl")
    val imageUrl: String
)

data class FetchedDishes(
    @Json(name="recipes")
    val recipes: List<DishResponse>
)
