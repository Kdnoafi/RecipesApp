package com.example.rsrafprojekat2david_maloparac_rn9519.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rsrafprojekat2david_maloparac_rn9519.R
import com.example.rsrafprojekat2david_maloparac_rn9519.databinding.FragmentListBinding
import com.example.rsrafprojekat2david_maloparac_rn9519.presentation.contract.MainContract
import com.example.rsrafprojekat2david_maloparac_rn9519.presentation.view.recycler.adapter.DishAdapter
import com.example.rsrafprojekat2david_maloparac_rn9519.presentation.view.states.DishesState
import com.example.rsrafprojekat2david_maloparac_rn9519.presentation.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class ListFragment : Fragment(R.layout.fragment_list) {
    private val mainViewModel: MainContract.ViewModel by sharedViewModel<MainViewModel>()

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: DishAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initUi()
        initObservers()
    }

    private fun initUi() {
        initRecycler()
        initListeners()
    }

    private fun initRecycler() {
        val llm1 = LinearLayoutManager(context)
        llm1.orientation = LinearLayoutManager.VERTICAL
        binding.listDish.layoutManager = llm1
        adapter = DishAdapter()
        binding.listDish.adapter = adapter

        // Klik na jelo
        adapter.onItemClick = { dish ->
            mainViewModel.current = dish

            val fragmentManager = (activity as FragmentActivity).supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.container, SingleDishFragment())
            transaction.addToBackStack("list")
            transaction.commit()
        }
    }

    private fun initListeners() {
       /* binding.inputEt.doAfterTextChanged {
            val filter = it.toString()
            mainViewModel.getDishesByName(filter)
        }*/
    }

    private fun initObservers() {
        mainViewModel.dishesState.observe(viewLifecycleOwner, Observer {
            Timber.e(it.toString())
            renderState(it)
        })

        mainViewModel.getAllDishes()
        mainViewModel.fetchAllDishes()
    }

    private fun renderState(state: DishesState) {
        when (state) {
            is DishesState.Success -> {
                showLoadingState(false)
                adapter.submitList(state.dishes)
            }
            is DishesState.Error -> {
                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is DishesState.DataFetched -> {
                showLoadingState(false)
            }
            is DishesState.Loading -> {
                showLoadingState(true)
            }
        }
    }

    private fun showLoadingState(loading: Boolean) {
        binding.listDish.isVisible = !loading
        binding.loadingDish.isVisible = loading
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}