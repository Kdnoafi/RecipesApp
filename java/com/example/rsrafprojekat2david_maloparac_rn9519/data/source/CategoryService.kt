package com.example.rsrafprojekat2david_maloparac_rn9519.data.source

import com.example.rsrafprojekat2david_maloparac_rn9519.data.model.category.CategoryResponse
import com.example.rsrafprojekat2david_maloparac_rn9519.data.model.category.FetchedCategories
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface CategoryService {

    @GET("api/v2/categories")
    fun getAll(): Observable<FetchedCategories>

    @Headers("Content-Type: application/json")
    @GET("api/v2/categories")
    fun getAll1(): Call<FetchedCategories>
}