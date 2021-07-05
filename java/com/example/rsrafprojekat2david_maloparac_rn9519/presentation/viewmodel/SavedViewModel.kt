package com.example.rsrafprojekat2david_maloparac_rn9519.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rsrafprojekat2david_maloparac_rn9519.data.model.dish.SavedDish
import com.example.rsrafprojekat2david_maloparac_rn9519.data.repositories.SavedDishRepository
import com.example.rsrafprojekat2david_maloparac_rn9519.presentation.contract.SavedContract
import com.example.rsrafprojekat2david_maloparac_rn9519.presentation.view.states.AddDishState
import com.example.rsrafprojekat2david_maloparac_rn9519.presentation.view.states.DishesState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import java.util.concurrent.TimeUnit

class SavedViewModel(
        private val dishRepository: SavedDishRepository
) : ViewModel(), SavedContract.ViewModel{

    override var current: SavedDish? = null
    private val subscriptions = CompositeDisposable()
    override val dishesState: MutableLiveData<DishesState> = MutableLiveData()
    override val addDone: MutableLiveData<AddDishState> = MutableLiveData()

    private val publishSubject: PublishSubject<String> = PublishSubject.create()

    init {
        val subscription = publishSubject
                .debounce(200, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .switchMap {
                    dishRepository
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
                            dishesState.value = DishesState.SaveSuccess(it)
                        },
                        {
                            dishesState.value = DishesState.Error("Error happened while fetching data from db")
                            Timber.e(it)
                        }
                )
        subscriptions.add(subscription)
    }

    override fun getAllDishes() {
        val subscription = dishRepository
                .getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            dishesState.value = DishesState.SaveSuccess(it)
                        },
                        {
                            dishesState.value = DishesState.Error("Error happened while fetching data from db")
                            Timber.e(it)
                        }
                )
        subscriptions.add(subscription)
    }

    override fun getDishesByName(name: String) {
        publishSubject.onNext(name)
    }

    override fun getDishesByCategory(category: String) {
        val subscription = dishRepository
                .getAllByCategory(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            dishesState.value = DishesState.SaveSuccess(it)
                        },
                        {
                            dishesState.value = DishesState.Error("Error happened while fetching data from db")
                            Timber.e(it)
                        }
                )
        subscriptions.add(subscription)
    }

    override fun addDish(dish: SavedDish) {
        val subscription = dishRepository
                .insert(dish)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            addDone.value = AddDishState.Success
                        },
                        {
                            addDone.value = AddDishState.Error("Error happened while adding dish")
                            Timber.e(it)
                        }
                )
        subscriptions.add(subscription)
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.dispose()
    }
}