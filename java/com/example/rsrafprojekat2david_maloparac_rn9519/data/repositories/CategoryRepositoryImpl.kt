package com.example.rsrafprojekat2david_maloparac_rn9519.data.repositories

import com.example.rsrafprojekat2david_maloparac_rn9519.data.model.Resource
import com.example.rsrafprojekat2david_maloparac_rn9519.data.model.category.Category
import com.example.rsrafprojekat2david_maloparac_rn9519.data.model.category.CategoryEntity
import com.example.rsrafprojekat2david_maloparac_rn9519.data.model.category.CategoryResponse
import com.example.rsrafprojekat2david_maloparac_rn9519.data.source.CategoryDao
import com.example.rsrafprojekat2david_maloparac_rn9519.data.source.CategoryService
import io.reactivex.Observable
import timber.log.Timber

class CategoryRepositoryImpl (
    private val localDataSource: CategoryDao,
    private val remoteDataSource: CategoryService
) : CategoryRepository {

    companion object {
        var counter: Int = 0
    }

    override fun fetchAll(): Observable<Resource<Unit>> {
        return remoteDataSource
                .getAll()
                .doOnNext {
                    val iterator = it.categories.iterator()
                    val entities: MutableList<CategoryEntity> = mutableListOf()
                    while(iterator.hasNext()) {
                        val resp: CategoryResponse = iterator.next()
                        val entity = CategoryEntity(counter++.toString(), resp.title, resp.imageUrl)
                        entities.add(entity)
                    }
                    localDataSource.deleteAndInsertAll(entities)
                }
                .map {
                    Resource.Success(Unit)
                }
    }

    override fun getAll(): Observable<List<Category>> {
        return localDataSource
                .getAll()
                .map {
                    it.map {
                        Category(it.id, it.title, it.imageUrl)
                    }
                }
    }

    override fun getAllByName(name: String): Observable<List<Category>> {
        return localDataSource
                .getByName(name)
                .map {
                    it.map {
                        Category(it.id, it.title, it.imageUrl)
                    }
                }
    }
}