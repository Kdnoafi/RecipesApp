package com.example.rsrafprojekat2david_maloparac_rn9519.data.repositories

import com.example.rsrafprojekat2david_maloparac_rn9519.data.model.Resource
import com.example.rsrafprojekat2david_maloparac_rn9519.data.model.dish.SavedDish
import io.reactivex.Completable
import io.reactivex.Observable

interface SavedDishRepository {

    fun getAll(): Observable<List<SavedDish>>
    fun getAllByName(name: String): Observable<List<SavedDish>>
    fun getAllByCategory(category: String): Observable<List<SavedDish>>
    fun insert(savedDish: SavedDish): Completable
}