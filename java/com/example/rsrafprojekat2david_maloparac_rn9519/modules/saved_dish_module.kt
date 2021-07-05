package com.example.rsrafprojekat2david_maloparac_rn9519.modules

import com.example.rsrafprojekat2david_maloparac_rn9519.data.repositories.SavedDishRepository
import com.example.rsrafprojekat2david_maloparac_rn9519.data.repositories.SavedDishRepositoryImpl
import com.example.rsrafprojekat2david_maloparac_rn9519.data.source.SavedDishDataBase
import com.example.rsrafprojekat2david_maloparac_rn9519.presentation.viewmodel.SavedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val savedDishModule = module {

    viewModel { SavedViewModel(dishRepository = get()) }

    single<SavedDishRepository> { SavedDishRepositoryImpl(localDataSource = get()) }

    single { get<SavedDishDataBase>().savedDishDao() }

    //single<DishService> { create(retrofit = get()) }
}