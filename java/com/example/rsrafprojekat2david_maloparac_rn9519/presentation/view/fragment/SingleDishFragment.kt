package com.example.rsrafprojekat2david_maloparac_rn9519.presentation.view.fragment

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.rsrafprojekat2david_maloparac_rn9519.R
import com.example.rsrafprojekat2david_maloparac_rn9519.data.model.dish.SavedDish
import com.example.rsrafprojekat2david_maloparac_rn9519.data.model.recipe.Recipe
import com.example.rsrafprojekat2david_maloparac_rn9519.databinding.FragmentDishBinding
import com.example.rsrafprojekat2david_maloparac_rn9519.presentation.contract.MainContract
import com.example.rsrafprojekat2david_maloparac_rn9519.presentation.contract.SavedContract
import com.example.rsrafprojekat2david_maloparac_rn9519.presentation.viewmodel.MainViewModel
import com.example.rsrafprojekat2david_maloparac_rn9519.presentation.viewmodel.SavedViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.lang.Math.ceil

class SingleDishFragment : Fragment(R.layout.fragment_dish) {

    private var _binding: FragmentDishBinding? = null
    private val binding get() = _binding!!

    private val savedViewModel: SavedContract.ViewModel by sharedViewModel<SavedViewModel>()
    private val mainViewModel: MainContract.ViewModel by sharedViewModel<MainViewModel>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDishBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        DishImage(binding.img, mainViewModel.current?.image!!).execute()
        binding.txtTitle.text = mainViewModel.current?.name!!

        mainViewModel.getById(mainViewModel.current?.id!!)
        val ingredients: List<String> = mainViewModel.recipe!!.ingredients
        var str: String = ""
        for(s: String in ingredients) {
            str += s
            str += '\n'
        }

        val rank: Double = mainViewModel.recipe!!.socialUrl.toDouble()
        binding.txtIngredients.setText(str)
        binding.txtRank.text = kotlin.math.ceil(rank).toString()

        binding.btnSave.setOnClickListener {
            // Pravim novi "savedDish", koji cu da prosledim na stranicu za cuvanje
            val savedDish = SavedDish(mainViewModel.current!!.id,
                    mainViewModel.current!!.name,
                    mainViewModel.current!!.image, "", str, "")

            savedViewModel.current = savedDish

            val fragmentManager = (activity as FragmentActivity).supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.container, SaveFragment())
            transaction.addToBackStack("single")
            transaction.commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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