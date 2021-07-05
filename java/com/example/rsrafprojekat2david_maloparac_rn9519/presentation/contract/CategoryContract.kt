package com.example.rsrafprojekat2david_maloparac_rn9519.presentation.contract

import androidx.lifecycle.LiveData
import com.example.rsrafprojekat2david_maloparac_rn9519.data.model.category.Category
import com.example.rsrafprojekat2david_maloparac_rn9519.presentation.view.states.AddCategoryState
import com.example.rsrafprojekat2david_maloparac_rn9519.presentation.view.states.CategoriesState

interface CategoryContract {

    interface ViewModel {

        var current: Category?
        val categoriesState: LiveData<CategoriesState>
        val addDone: LiveData<AddCategoryState>

        fun fetchAllCategories()
        fun getAllCategories()
        fun getCategoriesByName(name: String)
    }
}