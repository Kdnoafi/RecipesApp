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
import com.example.rsrafprojekat2david_maloparac_rn9519.data.repositories.DishRepositoryImpl
import com.example.rsrafprojekat2david_maloparac_rn9519.databinding.FragmentCategoryBinding
import com.example.rsrafprojekat2david_maloparac_rn9519.presentation.contract.CategoryContract
import com.example.rsrafprojekat2david_maloparac_rn9519.presentation.contract.MainContract
import com.example.rsrafprojekat2david_maloparac_rn9519.presentation.view.recycler.adapter.CategoryAdapter
import com.example.rsrafprojekat2david_maloparac_rn9519.presentation.view.recycler.adapter.DishAdapter
import com.example.rsrafprojekat2david_maloparac_rn9519.presentation.view.states.CategoriesState
import com.example.rsrafprojekat2david_maloparac_rn9519.presentation.view.states.DishesState
import com.example.rsrafprojekat2david_maloparac_rn9519.presentation.viewmodel.CategoryViewModel
import com.example.rsrafprojekat2david_maloparac_rn9519.presentation.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class CategoryFragment : Fragment(R.layout.fragment_category) {

    private val categoryViewModel: CategoryContract.ViewModel by sharedViewModel<CategoryViewModel>()
    private val mainViewModel: MainContract.ViewModel by sharedViewModel<MainViewModel>()

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var dishAdapter: DishAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
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
        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.VERTICAL
        binding.listCategory.layoutManager = llm

        categoryAdapter = CategoryAdapter()
        binding.listCategory.adapter = categoryAdapter

        // Klik na kategoriju
        categoryAdapter.onItemClick = { category ->
            DishRepositoryImpl.category = category.title

            val fragmentManager = (activity as FragmentActivity).supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.container, ListFragment())
            transaction.addToBackStack("category")
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
        categoryViewModel.categoriesState.observe(viewLifecycleOwner, Observer {
            Timber.e(it.toString())
            renderCategoryState(it)
        })
        categoryViewModel.getAllCategories()
        categoryViewModel.fetchAllCategories()
    }

    private fun renderCategoryState(state: CategoriesState) {
        when (state) {
            is CategoriesState.Success -> {
                showLoadingState(false)
                categoryAdapter.submitList(state.categories)
            }
            is CategoriesState.Error -> {
                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is CategoriesState.DataFetched -> {
                showLoadingState(false)
            }
            is CategoriesState.Loading -> {
                showLoadingState(true)
            }
        }
    }

    private fun showLoadingState(loading: Boolean) {
        binding.listCategory.isVisible = !loading
        binding.loadingPb.isVisible = loading
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}