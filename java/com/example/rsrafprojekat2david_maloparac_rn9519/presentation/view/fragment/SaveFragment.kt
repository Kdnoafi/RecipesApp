package com.example.rsrafprojekat2david_maloparac_rn9519.presentation.view.fragment

import android.app.DatePickerDialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rsrafprojekat2david_maloparac_rn9519.R
import com.example.rsrafprojekat2david_maloparac_rn9519.data.model.dish.SavedDish
import com.example.rsrafprojekat2david_maloparac_rn9519.data.source.SavedDishDataBase
import com.example.rsrafprojekat2david_maloparac_rn9519.databinding.FragmentSaveBinding
import com.example.rsrafprojekat2david_maloparac_rn9519.presentation.contract.CategoryContract
import com.example.rsrafprojekat2david_maloparac_rn9519.presentation.contract.SavedContract
import com.example.rsrafprojekat2david_maloparac_rn9519.presentation.viewmodel.CategoryViewModel
import com.example.rsrafprojekat2david_maloparac_rn9519.presentation.viewmodel.SavedViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class SaveFragment : Fragment(R.layout.fragment_save) {

    private var _binding: FragmentSaveBinding? = null
    private val binding get() = _binding!!

    private val savedViewModel: SavedContract.ViewModel by sharedViewModel<SavedViewModel>()
    private val categoryViewModel: CategoryContract.ViewModel by sharedViewModel<CategoryViewModel>()

    private var datePickerDialog: DatePickerDialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSaveBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initUi()
        initListeners()
    }

    private fun initUi() {
        binding.txtDishName.text = savedViewModel.current!!.name
        DishImage(binding.imgDish, savedViewModel.current!!.image).execute()

        val sdf = SimpleDateFormat("dd/M/yyyy")
        val currentDate = sdf.format(Date())
        binding.txtDate.text = currentDate
    }

    private fun initListeners() {
        binding.btnAdd.setOnClickListener {
            Timber.e(binding.txtDate.text.toString())
            savedViewModel.current!!.date = binding.txtDate.text.toString()
            savedViewModel.current!!.category = binding.categorySpinner.selectedItem.toString()
            savedViewModel.addDish(savedViewModel.current!!)

            Toast.makeText(activity, "Recipe saved", Toast.LENGTH_SHORT).show();
        }

        binding.txtDate.setOnClickListener {
            val c = Calendar.getInstance()
            val cyear = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = activity?.let {
                DatePickerDialog(it, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    val sb = StringBuilder()
                    sb.append(dayOfMonth).append("/").append(monthOfYear).append("/").append(year)
                    binding.txtDate.text = sb.toString()
                }, cyear, month, day)
            }

            dpd?.show()
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