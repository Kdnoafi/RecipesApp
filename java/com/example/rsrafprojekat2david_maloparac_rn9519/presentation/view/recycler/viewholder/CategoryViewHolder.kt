package com.example.rsrafprojekat2david_maloparac_rn9519.presentation.view.recycler.viewholder

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.rsrafprojekat2david_maloparac_rn9519.data.model.category.Category
import com.example.rsrafprojekat2david_maloparac_rn9519.databinding.LayoutItemCategoryBinding

class CategoryViewHolder(private val itemBinding: LayoutItemCategoryBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(category: Category, onClick: (category: Category) -> Unit) {
        itemBinding.categoryTitle.text = category.title
        CategoryImage(itemBinding.categoryImg, category.imageUrl).execute()

        itemBinding.root.setOnClickListener {
            onClick.invoke(category)
        }
    }

    private inner class CategoryImage(var imageView: ImageView, val url: String) : AsyncTask<String, Void, Bitmap?>() {

        override fun doInBackground(vararg urls: String): Bitmap? {
            var image: Bitmap? = null
            val `in` = java.net.URL(url).openStream()
            image = BitmapFactory.decodeStream(`in`)
            return image
        }

        override fun onPostExecute(result: Bitmap?) {
            imageView.setImageBitmap(result)
        }
    }
}