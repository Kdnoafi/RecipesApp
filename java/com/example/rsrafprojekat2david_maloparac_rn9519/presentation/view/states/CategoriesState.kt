package com.example.rsrafprojekat2david_maloparac_rn9519.presentation.view.states

import com.example.rsrafprojekat2david_maloparac_rn9519.data.model.category.Category

sealed class CategoriesState {

    object Loading: CategoriesState()
    object DataFetched: CategoriesState()
    data class Success(val categories: List<Category>): CategoriesState()
    data class Error(val message: String): CategoriesState()
}