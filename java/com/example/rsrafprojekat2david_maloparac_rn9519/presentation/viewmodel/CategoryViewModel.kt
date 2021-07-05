package com.example.rsrafprojekat2david_maloparac_rn9519.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rsrafprojekat2david_maloparac_rn9519.data.model.Resource
import com.example.rsrafprojekat2david_maloparac_rn9519.data.model.category.Category
import com.example.rsrafprojekat2david_maloparac_rn9519.data.repositories.CategoryRepository
import com.example.rsrafprojekat2david_maloparac_rn9519.presentation.contract.CategoryContract
import com.example.rsrafprojekat2david_maloparac_rn9519.presentation.view.states.AddCategoryState
import com.example.rsrafprojekat2david_maloparac_rn9519.presentation.view.states.CategoriesState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import java.util.concurrent.TimeUnit

class CategoryViewModel(
        private val categoryRepository: CategoryRepository
) : ViewModel(), CategoryContract.ViewModel {

    override var current: Category? = null
    private val subscriptions = CompositeDisposable()
    override val categoriesState: MutableLiveData<CategoriesState> = MutableLiveData()
    override val addDone: MutableLiveData<AddCategoryState> = MutableLiveData()

    private val publishSubject: PublishSubject<String> = PublishSubject.create()

    init {
        val subscription = publishSubject
                .debounce(200, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .switchMap {
                    categoryRepository
                            .getAllByName(it)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnError {
                                Timber.e("Error in publish subject")
                                Timber.e(it)
                            }
                }
                .subscribe(
                        {
                            categoriesState.value = CategoriesState.Success(it)
                        },
                        {
                            categoriesState.value = CategoriesState.Error("Error happened while fetching data from db")
                            Timber.e(it)
                        }
                )
        subscriptions.add(subscription)
    }

    override fun fetchAllCategories() {
        val subscription = categoryRepository
                .fetchAll()
                .startWith(Resource.Loading())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            when(it) {
                                is Resource.Loading -> categoriesState.value = CategoriesState.Loading
                                is Resource.Success -> categoriesState.value = CategoriesState.DataFetched
                                is Resource.Error -> categoriesState.value = CategoriesState.Error("Error happened while fetching data from the server")
                            }
                        },
                        {
                            categoriesState.value = CategoriesState.Error("Error happened while fetching data from the server")
                            Timber.e(it)
                        }
                )
        subscriptions.add(subscription)
    }

    override fun getAllCategories() {
        val subscription = categoryRepository
                .getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            categoriesState.value = CategoriesState.Success(it)
                        },
                        {
                            categoriesState.value = CategoriesState.Error("Error happened while fetching data from db")
                            Timber.e(it)
                        }
                )
        subscriptions.add(subscription)
    }

    override fun getCategoriesByName(name: String) {
        publishSubject.onNext(name)
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.dispose()
    }
}