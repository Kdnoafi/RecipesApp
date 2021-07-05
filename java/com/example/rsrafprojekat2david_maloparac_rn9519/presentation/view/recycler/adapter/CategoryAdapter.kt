package com.example.rsrafprojekat2david_maloparac_rn9519.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.rsrafprojekat2david_maloparac_rn9519.data.model.category.Category
import com.example.rsrafprojekat2david_maloparac_rn9519.databinding.LayoutItemCategoryBinding
import com.example.rsrafprojekat2david_maloparac_rn9519.presentation.view.recycler.diff.CategoryDiffCallback
import com.example.rsrafprojekat2david_maloparac_rn9519.presentation.view.recycler.viewholder.CategoryViewHolder

class CategoryAdapter : ListAdapter<Category, CategoryViewHolder>(CategoryDiffCallback()) {

    lateinit var onItemClick: (category: Category) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemBinding = LayoutItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClick)
    }
}