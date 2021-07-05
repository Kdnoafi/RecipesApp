package com.example.rsrafprojekat2david_maloparac_rn9519.presentation.view.states

import com.example.rsrafprojekat2david_maloparac_rn9519.data.model.dish.Dish
import com.example.rsrafprojekat2david_maloparac_rn9519.data.model.dish.SavedDish

sealed class DishesState {

    object Loading: DishesState()
    object DataFetched: DishesState()
    data class Success(val dishes: List<Dish>): DishesState()
    data class SaveSuccess(val saved: List<SavedDish>): DishesState()
    data class Error(val message: String): DishesState()
}