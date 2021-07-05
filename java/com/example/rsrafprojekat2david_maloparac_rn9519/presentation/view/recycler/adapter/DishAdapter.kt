package com.example.rsrafprojekat2david_maloparac_rn9519.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.rsrafprojekat2david_maloparac_rn9519.data.model.dish.Dish
import com.example.rsrafprojekat2david_maloparac_rn9519.databinding.LayoutItemDishBinding
import com.example.rsrafprojekat2david_maloparac_rn9519.presentation.view.recycler.diff.DishDiffCallback
import com.example.rsrafprojekat2david_maloparac_rn9519.presentation.view.recycler.viewholder.DishViewHolder

class DishAdapter : ListAdapter<Dish, DishViewHolder>(DishDiffCallback()) {

    lateinit var onItemClick: (dish: Dish) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishViewHolder {
        val itemBinding = LayoutItemDishBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DishViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: DishViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClick)
    }
}