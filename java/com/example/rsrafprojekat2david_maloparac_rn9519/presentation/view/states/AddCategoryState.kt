package com.example.rsrafprojekat2david_maloparac_rn9519.presentation.view.states

sealed class AddCategoryState {

    object Success: AddCategoryState()
    data class Error(val message: String): AddCategoryState()
}