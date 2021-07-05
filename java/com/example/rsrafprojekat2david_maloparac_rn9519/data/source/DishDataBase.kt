package com.example.rsrafprojekat2david_maloparac_rn9519.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.rsrafprojekat2david_maloparac_rn9519.data.model.dish.DishEntity

@Database(
    entities = [DishEntity::class],
    version = 1,
    exportSchema = false)
@TypeConverters()
abstract class DishDataBase : RoomDatabase() {
    abstract fun getDishDao(): DishDao
}