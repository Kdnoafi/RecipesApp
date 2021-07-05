package com.example.rsrafprojekat2david_maloparac_rn9519.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rsrafprojekat2david_maloparac_rn9519.R
import com.example.rsrafprojekat2david_maloparac_rn9519.databinding.FragmentSavedBinding
import com.example.rsrafprojekat2david_maloparac_rn9519.presentation.contract.CategoryContract
import com.example.rsrafprojekat2david_maloparac_rn9519.presentation.contract.MainContract
import com.example.rsrafprojekat2david_maloparac_rn9519.presentation.contract.SavedContract
import com.example.rsrafprojekat2david_maloparac_rn9519.presentation.view.recycler.adapter.SavedDishAdapter
import com.example.rsrafprojekat2david_maloparac_rn9519.presentation.view.states.DishesState
import com.example.rsrafprojekat2david_maloparac_rn9519.presentation.viewmodel.CategoryViewModel
import com.example.rsrafprojekat2david_maloparac_rn9519.presentation.viewmodel.MainViewModel
import com.example.rsrafprojekat2david_maloparac_rn9519.presentation.viewmodel.SavedViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class SavedFragment : Fragment(R.layout.fragment_saved) {

    private val savedViewModel: SavedContract.ViewModel by sharedViewModel<SavedViewModel>()
    private val mainViewModel: MainContract.ViewModel by sharedViewModel<MainViewModel>()
    private val categoryViewModel: CategoryContract.ViewModel by sharedViewModel<CategoryViewModel>()

    private var _binding: FragmentSavedBinding? = null
    private val binding get() = _binding!!

    private lateinit var dishAdapter: SavedDishAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSavedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        init()
    }

    private fun init() {
        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.VERTICAL
        binding.listSaved.layoutManager = llm

        dishAdapter = SavedDishAdapter()
        binding.listSaved.adapter = dishAdapter

        dishAdapter.onItemClick = { dish ->
            mainViewModel.current = dish

            val fragmentManager = (activity as FragmentActivity).supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.container, SingleSavedDishFragment())
            transaction.addToBackStack("saved")
            transaction.commit()
        }
    }

    private fun initObservers() {
        savedViewModel.dishesState.observe(viewLifecycleOwner, Observer {
            Timber.e(it.toString())
            renderDishState(it)
        })

        savedViewModel.getDishesByCategory(categoryViewModel.current!!.title)

        mainViewModel.getAllDishes()
        mainViewModel.fetchAllDishes()
    }

    private fun renderDishState(state: DishesState) {
        when (state) {
            is DishesState.SaveSuccess -> {
                dishAdapter.submitList(state.saved)
            }
            is DishesState.Error -> {
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is DishesState.DataFetched -> {
            }
        }
    }
}