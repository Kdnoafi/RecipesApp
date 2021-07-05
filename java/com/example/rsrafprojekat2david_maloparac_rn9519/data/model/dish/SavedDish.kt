package com.example.rsrafprojekat2david_maloparac_rn9519.data.model.dish

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved")
data class SavedDish (
        @PrimaryKey
        var id: String,
        val name: String,
        val image: String,
        var date: String,
        val recipe: String,
        var category: String
)