package com.example.rsrafprojekat2david_maloparac_rn9519.data.source

import androidx.room.*
import com.example.rsrafprojekat2david_maloparac_rn9519.data.model.dish.DishEntity
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
abstract class DishDao {

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insert(entity: DishEntity): Completable

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insertAll(entities: List<DishEntity>): Completable

    @Query("SELECT * FROM dishes")
    abstract fun getAll(): Observable<List<DishEntity>>

    @Query("DELETE FROM dishes")
    abstract fun deleteAll()

    @Transaction
    open fun deleteAndInsertAll(entities: List<DishEntity>) {
        deleteAll()
        insertAll(entities).blockingAwait()
    }

    @Query("SELECT * FROM dishes WHERE name LIKE :name || '%'")
    abstract fun getByName(name: String): Observable<List<DishEntity>>
}