package com.tutorial.basicmvvm.viewmodel

import android.app.Application
import android.text.TextUtils
import androidx.arch.core.util.Function
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.Transformations
import com.tutorial.basicmvvm.App
import com.tutorial.basicmvvm.DataRepository
import com.tutorial.basicmvvm.db.entity.ProductEntity

class ProductListViewModel(application: Application, private val savedStateHandle: SavedStateHandle) :
    AndroidViewModel(application) {
    companion object {
        const val QUERY_KEY = "query"
    }

    private var repository: DataRepository = (application as App).getDataRepository()
    private var products: LiveData<List<ProductEntity>> = Transformations.switchMap(
        savedStateHandle.getLiveData(QUERY_KEY, null),
        Function<CharSequence?, LiveData<List<ProductEntity>>> { query: CharSequence? ->
            if (TextUtils.isEmpty(query)) {
                return@Function repository.getProducts()
            }
            repository.searchProducts("*$query*")
        })

    fun setQuery(query: CharSequence) = savedStateHandle.set(QUERY_KEY, query)

    fun getProducts(): LiveData<List<ProductEntity>> = products

}