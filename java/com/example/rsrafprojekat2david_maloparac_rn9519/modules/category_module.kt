package com.example.rsrafprojekat2david_maloparac_rn9519.modules

import com.example.rsrafprojekat2david_maloparac_rn9519.data.repositories.CategoryRepository
import com.example.rsrafprojekat2david_maloparac_rn9519.data.repositories.CategoryRepositoryImpl
import com.example.rsrafprojekat2david_maloparac_rn9519.data.source.CategoryDataBase
import com.example.rsrafprojekat2david_maloparac_rn9519.data.source.CategoryService
import com.example.rsrafprojekat2david_maloparac_rn9519.presentation.viewmodel.CategoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val categoryModule = module {

    viewModel { CategoryViewModel(categoryRepository = get()) }

    single<CategoryRepository> { CategoryRepositoryImpl(localDataSource = get(), remoteDataSource = get()) }

    single { get<CategoryDataBase>().getCategoryDao() }

    single<CategoryService> { create(retrofit = get()) }
}