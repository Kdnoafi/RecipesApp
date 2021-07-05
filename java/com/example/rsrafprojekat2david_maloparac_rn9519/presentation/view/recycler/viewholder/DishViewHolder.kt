package com.example.rsrafprojekat2david_maloparac_rn9519.presentation.view.recycler.viewholder

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.rsrafprojekat2david_maloparac_rn9519.data.model.dish.Dish
import com.example.rsrafprojekat2david_maloparac_rn9519.databinding.LayoutItemDishBinding

class DishViewHolder(private val itemBinding: LayoutItemDishBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(dish: Dish, onClick: (dish: Dish) -> Unit) {
        itemBinding.dishTitle.text = dish.name
        DishImage(itemBinding.dishImg, dish.image).execute()

        itemBinding.root.setOnClickListener {
            onClick.invoke(dish)
        }
    }

    private inner class DishImage(var imageView: ImageView, val url: String) : AsyncTask<String, Void, Bitmap?>() {

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