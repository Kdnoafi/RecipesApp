package com.example.rsrafprojekat2david_maloparac_rn9519.data.repositories

import com.example.rsrafprojekat2david_maloparac_rn9519.data.model.Resource
import com.example.rsrafprojekat2david_maloparac_rn9519.data.model.dish.*
import com.example.rsrafprojekat2david_maloparac_rn9519.data.source.SavedDishDao
import io.reactivex.Completable
import io.reactivex.Observable
import timber.log.Timber

class SavedDishRepositoryImpl(
        private val localDataSource: SavedDishDao
) : SavedDishRepository {

    companion object {
        var category: String = ""
    }

    override fun getAll(): Observable<List<SavedDish>> {
        return localDataSource
                .getAll()
                .map {
                    it.map {
                        SavedDish(it.id, it.name, it.image, it.date, it.recipe, it.category)
                    }
                }
    }

    override fun getAllByName(name: String): Observable<List<SavedDish>> {
        return localDataSource
                .getByName(name)
    }

    override fun getAllByCategory(category: String): Observable<List<SavedDish>> {
        return localDataSource
                .getByCategory(category)
                .map {
                    it.map {
                        SavedDish(it.id, it.name, it.image, it.date, it.recipe, it.category)
                    }
                }
    }

    override fun insert(savedDish: SavedDish): Completable {
        return localDataSource
                .insert(savedDish)
    }
}