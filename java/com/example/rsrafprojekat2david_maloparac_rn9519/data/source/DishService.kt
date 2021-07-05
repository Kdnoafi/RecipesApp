package com.example.rsrafprojekat2david_maloparac_rn9519.data.source

import com.example.rsrafprojekat2david_maloparac_rn9519.data.model.dish.FetchedDishes
import com.example.rsrafprojekat2david_maloparac_rn9519.data.model.recipe.FetchedRecipe
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DishService {

    @GET("api/v2/recipes/")
    fun getAll(@Query("page") page: Int, @Query("q") category: String): Observable<FetchedDishes>

    @GET("api/get/")
    fun getById(@Query("rId") id: String): Single<FetchedRecipe>

    @GET("api/v2/recipes?q=Chicken&page=1")
    fun getChicken(): Observable<FetchedDishes>

    @GET("api/v2/recipes?q=Pizza&page=1")
    fun getPizza(): Observable<FetchedDishes>

    @GET("api/v2/recipes?q=Breakfast&page=1")
    fun getBreakfast(): Observable<FetchedDishes>

    @GET("api/v2/recipes?q=Bacon&page=1")
    fun getBacon(): Observable<FetchedDishes>

    @GET("api/v2/recipes?q=Steak&page=1")
    fun getSteak(): Observable<FetchedDishes>

    @GET("api/v2/recipes?q=Indian&page=1")
    fun getIndian(): Observable<FetchedDishes>

    @GET("api/v2/recipes?q=Asian&page=1")
    fun getAsian(): Observable<FetchedDishes>
}