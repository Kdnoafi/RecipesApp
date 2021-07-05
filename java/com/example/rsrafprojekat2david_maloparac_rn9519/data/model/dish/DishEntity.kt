package com.example.rsrafprojekat2david_maloparac_rn9519.data.model.dish

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dishes")
data class DishEntity (
    @PrimaryKey
    val id: String,
    val name: String,
    val image: String
)