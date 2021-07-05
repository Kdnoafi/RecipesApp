package com.example.rsrafprojekat2david_maloparac_rn9519.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rsrafprojekat2david_maloparac_rn9519.data.model.dish.Dish
import com.example.rsrafprojekat2david_maloparac_rn9519.data.model.Resource
import com.example.rsrafprojekat2david_maloparac_rn9519.data.model.recipe.FetchedRecipe
import com.example.rsrafprojekat2david_maloparac_rn9519.data.model.recipe.Recipe
import com.example.rsrafprojekat2david_maloparac_rn9519.data.model.recipe.RecipeResponse
import com.example.rsrafprojekat2david_maloparac_rn9519.data.repositories.DishRepository
import com.example.rsrafprojekat2david_maloparac_rn9519.presentation.contract.MainContract
import com.example.rsrafprojekat2david_maloparac_rn9519.presentation.view.states.AddDishState
import com.example.rsrafprojekat2david_maloparac_rn9519.presentation.view.states.DishesState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import java.util.concurrent.TimeUnit

class MainViewModel(
    private val dishRepository: DishRepository
) : ViewModel(), MainContract.ViewModel{

    override var current: Dish? = null
    override var recipe: Recipe? = null
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
                    dishesState.value = DishesState.Success(it)
                },
                {
                    dishesState.value = DishesState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun fetchAllDishes() {
        val subscription = dishRepository
            .fetchAll()
            .startWith(Resource.Loading())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when(it) {
                        is Resource.Loading -> dishesState.value = DishesState.Loading
                        is Resource.Success -> dishesState.value = DishesState.DataFetched
                        is Resource.Error -> dishesState.value = DishesState.Error("Error happened while fetching data from the server")
                    }
                },
                {
                    dishesState.value = DishesState.Error("Error happened while fetching data from the server")
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
                    dishesState.value = DishesState.Success(it)
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

    override fun addDish(dish: Dish) {
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

    override fun getById(rId: String) {
        val thread: Thread = Thread {
            val recipeResponse: RecipeResponse = dishRepository.getById(rId)
            recipe = Recipe(recipeResponse.social_rank, recipeResponse.ingredients)
        }

        thread.start()
        thread.join()
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.dispose()
    }
}