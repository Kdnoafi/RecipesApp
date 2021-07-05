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
import com.example.rsrafprojekat2david_maloparac_rn9519.databinding.FragmentDishBinding
import com.example.rsrafprojekat2david_maloparac_rn9519.databinding.FragmentSingleSavedBinding
import com.example.rsrafprojekat2david_maloparac_rn9519.presentation.contract.MainContract
import com.example.rsrafprojekat2david_maloparac_rn9519.presentation.contract.SavedContract
import com.example.rsrafprojekat2david_maloparac_rn9519.presentation.viewmodel.MainViewModel
import com.example.rsrafprojekat2david_maloparac_rn9519.presentation.viewmodel.SavedViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SingleSavedDishFragment : Fragment(R.layout.fragment_single_saved) {

    private var _binding: FragmentSingleSavedBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainContract.ViewModel by sharedViewModel<MainViewModel>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSingleSavedBinding.inflate(inflater, container, false)
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