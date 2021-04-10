package com.tutorial.basicmvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.tutorial.basicmvvm.db.AppDatabase
import com.tutorial.basicmvvm.db.entity.CommentEntity
import com.tutorial.basicmvvm.db.entity.ProductEntity

class DataRepository(private val database: AppDatabase) {
    private var observableProducts: MediatorLiveData<List<ProductEntity>> = MediatorLiveData()

    companion object {
        private var INSTANCE: DataRepository? = null

        fun getInstance(database: AppDatabase): DataRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = DataRepository(database)
                INSTANCE = instance
                instance
            }
        }
    }

    init {
        observableProducts.addSource(database.productDao().getAllProducts()) { productEntities ->
            if (database.isDatabaseCreated.value != null) {
                observableProducts.postValue(productEntities)
            }
        }
    }

    fun getProducts(): LiveData<List<ProductEntity>> = observableProducts

    fun loadProducts(productId: Int): LiveData<ProductEntity> =
        database.productDao().getProduct(productId)

    fun loadComments(productId: Int): LiveData<List<CommentEntity>> =
        database.commentDao().getProductComments(productId)

    fun searchProducts(query: String): LiveData<List<ProductEntity>> =
        database.productDao().search(query)


}