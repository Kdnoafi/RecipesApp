package com.example.rsrafprojekat2david_maloparac_rn9519.data.model.category

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class CategoryEntity (
    @PrimaryKey
    val id: String,
    val title: String,
    val imageUrl: String
)