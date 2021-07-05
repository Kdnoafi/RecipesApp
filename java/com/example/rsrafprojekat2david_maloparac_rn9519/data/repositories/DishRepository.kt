package com.example.rsrafprojekat2david_maloparac_rn9519.data.repositories

import com.example.rsrafprojekat2david_maloparac_rn9519.data.model.dish.Dish
import com.example.rsrafprojekat2david_maloparac_rn9519.data.model.Resource
import com.example.rsrafprojekat2david_maloparac_rn9519.data.model.recipe.FetchedRecipe
import com.example.rsrafprojekat2david_maloparac_rn9519.data.model.recipe.Recipe
import com.example.rsrafprojekat2david_maloparac_rn9519.data.model.recipe.RecipeResponse
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface DishRepository {

    fun fetchAll(): Observable<Resource<Unit>>
    fun getAll(): Observable<List<Dish>>
    fun getAllByName(name: String): Observable<List<Dish>>
    fun insert(dish: Dish): Completable
    fun getById(rId: String): RecipeResponse
}