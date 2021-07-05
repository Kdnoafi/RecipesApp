package com.example.rsrafprojekat2david_maloparac_rn9519.data.source

import androidx.room.*
import com.example.rsrafprojekat2david_maloparac_rn9519.data.model.category.CategoryEntity
import com.example.rsrafprojekat2david_maloparac_rn9519.data.model.dish.DishEntity
import io.reactivex.Completable
import io.reactivex.Observable
import timber.log.Timber

@Dao
abstract class CategoryDao {

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insert(entity: CategoryEntity): Completable

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insertAll(entities: List<CategoryEntity>): Completable

    @Query("SELECT * FROM categories")
    abstract fun getAll(): Observable<List<CategoryEntity>>

    @Query("DELETE FROM categories")
    abstract fun deleteAll()

    @Transaction
    open fun deleteAndInsertAll(entities: List<CategoryEntity>) {
        deleteAll()
        insertAll(entities).blockingAwait()
    }

    @Query("SELECT * FROM categories WHERE title LIKE :name || '%'")
    abstract fun getByName(name: String): Observable<List<CategoryEntity>>
}