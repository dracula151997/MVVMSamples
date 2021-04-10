package com.tutorial.basicmvvm.db.entity

import androidx.room.Entity
import androidx.room.Fts4

@Entity(tableName = "productsFts")
@Fts4(contentEntity = ProductEntity::class)
data class ProductFtsEntity(
    private val name: String,
    private val description: String
) {

}