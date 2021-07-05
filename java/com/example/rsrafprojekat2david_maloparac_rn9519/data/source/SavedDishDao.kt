package com.example.rsrafprojekat2david_maloparac_rn9519.data.source

import androidx.room.*
import com.example.rsrafprojekat2david_maloparac_rn9519.data.model.dish.DishEntity
import com.example.rsrafprojekat2david_maloparac_rn9519.data.model.dish.SavedDish
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
abstract class SavedDishDao {

    @Query("SELECT * FROM saved")
    abstract fun getAll(): Observable<List<SavedDish>>

    @Query("SELECT * FROM saved WHERE category LIKE :category")
    abstract fun getByCategory(category: String): Observable<List<SavedDish>>

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insert(dish: SavedDish): Completable

    @Delete
    abstract fun delete(dish: SavedDish)

    @Query("SELECT * FROM saved WHERE name LIKE :name || '%'")
    abstract fun getByName(name: String): Observable<List<SavedDish>>
}