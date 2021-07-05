package com.example.rsrafprojekat2david_maloparac_rn9519.data.model.category

import com.squareup.moshi.Json

data class CategoryResponse (
    @Json(name="title")
    val title: String,
    @Json(name="imageUrl")
    val imageUrl: String
)

data class FetchedCategories (
    @Json(name="categories")
    val categories: List<CategoryResponse>
)

