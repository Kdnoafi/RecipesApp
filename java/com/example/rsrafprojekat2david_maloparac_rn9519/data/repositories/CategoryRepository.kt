package com.example.rsrafprojekat2david_maloparac_rn9519.data.repositories

import com.example.rsrafprojekat2david_maloparac_rn9519.data.model.Resource
import com.example.rsrafprojekat2david_maloparac_rn9519.data.model.category.Category
import io.reactivex.Completable
import io.reactivex.Observable

interface CategoryRepository {

    fun fetchAll(): Observable<Resource<Unit>>
    fun getAll(): Observable<List<Category>>
    fun getAllByName(name: String): Observable<List<Category>>
}