package com.tutorial.basicmvvm.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tutorial.basicmvvm.db.entity.ProductEntity

@Dao
interface ProductDao {
    @Query("SELECT * FROM products")
    fun getAllProducts(): LiveData<List<ProductEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(products: List<ProductEntity>)

    @Query("SELECT * FROM products WHERE id= :productId")
    fun getProduct(productId: Int): LiveData<ProductEntity>

    @Query("SELECT * FROM products JOIN productsFts ON (products.id = productsFts.rowId) WHERE productsFts MATCH :query")
    fun search(query: String)

}