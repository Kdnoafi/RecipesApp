package com.example.rsrafprojekat2david_maloparac_rn9519.presentation.contract

import androidx.lifecycle.LiveData
import com.example.rsrafprojekat2david_maloparac_rn9519.data.model.category.Category
import com.example.rsrafprojekat2david_maloparac_rn9519.data.model.dish.Dish
import com.example.rsrafprojekat2david_maloparac_rn9519.data.model.recipe.Recipe
import com.example.rsrafprojekat2david_maloparac_rn9519.presentation.view.states.AddDishState
import com.example.rsrafprojekat2david_maloparac_rn9519.presentation.view.states.DishesState

interface MainContract {

    interface ViewModel {

        var current: Dish?
        var recipe: Recipe?
        val dishesState: LiveData<DishesState>
        val addDone: LiveData<AddDishState>

        fun fetchAllDishes()
        fun getAllDishes()
        fun getDishesByName(name: String)
        fun addDish(dish: Dish)
        fun getById(rId: String)
    }
}