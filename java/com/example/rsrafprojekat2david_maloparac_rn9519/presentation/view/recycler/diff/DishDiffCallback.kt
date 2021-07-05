package com.example.rsrafprojekat2david_maloparac_rn9519.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import com.example.rsrafprojekat2david_maloparac_rn9519.data.model.dish.Dish

class DishDiffCallback : DiffUtil.ItemCallback<Dish>() {

    override fun areItemsTheSame(oldItem: Dish, newItem: Dish): Boolean {
        return oldItem.id == newItem.id;
    }

    override fun areContentsTheSame(oldItem: Dish, newItem: Dish): Boolean {
        return oldItem.name == newItem.name;
    }
}