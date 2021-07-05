package com.example.rsrafprojekat2david_maloparac_rn9519.modules

import com.example.rsrafprojekat2david_maloparac_rn9519.data.repositories.DishRepository
import com.example.rsrafprojekat2david_maloparac_rn9519.data.repositories.DishRepositoryImpl
import com.example.rsrafprojekat2david_maloparac_rn9519.data.source.DishDataBase
import com.example.rsrafprojekat2david_maloparac_rn9519.data.source.DishService
import com.example.rsrafprojekat2david_maloparac_rn9519.presentation.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val dishModule = module {

    viewModel { MainViewModel(dishRepository = get()) }

    single<DishRepository> { DishRepositoryImpl(localDataSource = get(), remoteDataSource = get()) }

    single { get<DishDataBase>().getDishDao() }

    single<DishService> { create(retrofit = get()) }
}