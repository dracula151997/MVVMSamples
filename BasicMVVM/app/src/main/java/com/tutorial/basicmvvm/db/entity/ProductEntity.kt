package com.tutorial.basicmvvm.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tutorial.basicmvvm.model.Product


@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey
    var id: Int? = null,
    var name: String? = null,
    var description: String? = null,
    var price: Int? = null,

    ) : Product {


    override fun id(): Int = id!!

    override fun name(): String = name!!

    override fun description(): String = description!!

    override fun price(): Int = price!!
}