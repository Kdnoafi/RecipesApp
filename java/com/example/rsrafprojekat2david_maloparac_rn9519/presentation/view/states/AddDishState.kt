package com.example.rsrafprojekat2david_maloparac_rn9519.presentation.view.states

sealed class AddDishState {

    object Success: AddDishState()
    data class Error(val message: String): AddDishState()
}