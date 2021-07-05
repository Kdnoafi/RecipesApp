package com.example.rsrafprojekat2david_maloparac_rn9519.data.repositories

import com.example.rsrafprojekat2david_maloparac_rn9519.data.model.dish.Dish
import com.example.rsrafprojekat2david_maloparac_rn9519.data.model.dish.DishEntity
import com.example.rsrafprojekat2david_maloparac_rn9519.data.model.Resource
import com.example.rsrafprojekat2david_maloparac_rn9519.data.model.dish.DishResponse
import com.example.rsrafprojekat2david_maloparac_rn9519.data.model.dish.FetchedDishes
import com.example.rsrafprojekat2david_maloparac_rn9519.data.model.recipe.FetchedRecipe
import com.example.rsrafprojekat2david_maloparac_rn9519.data.model.recipe.Recipe
import com.example.rsrafprojekat2david_maloparac_rn9519.data.model.recipe.RecipeResponse
import com.example.rsrafprojekat2david_maloparac_rn9519.data.source.DishDao
import com.example.rsrafprojekat2david_maloparac_rn9519.data.source.DishService
import com.example.rsrafprojekat2david_maloparac_rn9519.modules.createRetrofit
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class DishRepositoryImpl(
    private val localDataSource: DishDao,
    private val remoteDataSource: DishService
) : DishRepository {

    companion object {
        var category: String = ""
    }

    override fun fetchAll(): Observable<Resource<Unit>> {
        return remoteDataSource
                .getAll(1, category)
                .doOnNext {
                    val iterator = it.recipes.iterator()
                    val entities: MutableList<DishEntity> = mutableListOf()
                    while(iterator.hasNext()) {
                        val resp: DishResponse = iterator.next()
                        val entity = DishEntity(resp.id, resp.title, resp.imageUrl)
                        entities.add(entity)
                    }
                    localDataSource.deleteAndInsertAll(entities)
                }
                .map {
                    Resource.Success(Unit)
                }
    }

    override fun getAll(): Observable<List<Dish>> {
        return localDataSource
            .getAll()
            .map {
                it.map {
                    Dish(it.id, it.name, it.image)
                }
            }
    }

    override fun getAllByName(name: String): Observable<List<Dish>> {
        return localDataSource
            .getByName(name)
            .map {
                it.map {
                    Dish(it.id, it.name, it.image)
                }
            }
    }

    override fun insert(dish: Dish): Completable {
        val dishEntity = DishEntity(dish.id, dish.name, "")
        return localDataSource
            .insert(dishEntity)
    }

    override fun getById(rId: String): RecipeResponse {
        val response: Single<FetchedRecipe> = remoteDataSource.getById(rId)
        return response.blockingGet().recipe
    }

    fun get(): Observable<Resource<Unit>> {
        var getCallback: (Observable<FetchedDishes>)? = null

        if(category == "Pizza") {
            getCallback = remoteDataSource.getPizza()
        } else if(category == "Breakfast") {
            getCallback = remoteDataSource.getBreakfast()
        } else if(category == "Bacon") {
            getCallback = remoteDataSource.getBacon()
        } else if(category == "Steak") {
            getCallback = remoteDataSource.getSteak()
        } else if(category == "Indian") {
            getCallback = remoteDataSource.getIndian()
        } else if(category == "Asian") {
            getCallback = remoteDataSource.getAsian()
        }

        return getCallback!!
                .doOnNext {
                    Timber.e("Upis u bazu")
                    val iterator = it.recipes.iterator()
                    val entities: MutableList<DishEntity> = mutableListOf()
                    while(iterator.hasNext()) {
                        val resp: DishResponse = iterator.next()
                        val entity = DishEntity(resp.id, resp.title, resp.imageUrl)
                        entities.add(entity)
                    }
                    localDataSource.deleteAndInsertAll(entities)
                }
                .map {
                    Resource.Success(Unit)
                }
    }
}