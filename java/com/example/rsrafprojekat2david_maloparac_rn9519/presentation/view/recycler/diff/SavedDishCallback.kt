package com.example.rsrafprojekat2david_maloparac_rn9519.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import com.example.rsrafprojekat2david_maloparac_rn9519.data.model.dish.Dish
import com.example.rsrafprojekat2david_maloparac_rn9519.data.model.dish.SavedDish

class SavedDishCallback : DiffUtil.ItemCallback<SavedDish>() {

    override fun areItemsTheSame(oldItem: SavedDish, newItem: SavedDish): Boolean {
        return oldItem.id == newItem.id;
    }

    override fun areContentsTheSame(oldItem: SavedDish, newItem: SavedDish): Boolean {
        return oldItem.name == newItem.name;
    }
}