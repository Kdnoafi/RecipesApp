package com.example.rsrafprojekat2david_maloparac_rn9519.presentation.contract

import androidx.lifecycle.LiveData
import com.example.rsrafprojekat2david_maloparac_rn9519.data.model.dish.Dish
import com.example.rsrafprojekat2david_maloparac_rn9519.data.model.dish.SavedDish
import com.example.rsrafprojekat2david_maloparac_rn9519.presentation.view.states.AddDishState
import com.example.rsrafprojekat2david_maloparac_rn9519.presentation.view.states.DishesState

interface SavedContract {

    interface ViewModel {

        var current: SavedDish?
        val dishesState: LiveData<DishesState>
        val addDone: LiveData<AddDishState>

        //fun fetchAllDishes()
        fun getAllDishes()
        fun getDishesByName(name: String)
        fun getDishesByCategory(category: String)
        fun addDish(dish: SavedDish)
    }
}
