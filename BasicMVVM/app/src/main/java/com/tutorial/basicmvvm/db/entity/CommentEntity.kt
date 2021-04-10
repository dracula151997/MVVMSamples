package com.tutorial.basicmvvm.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "comments",
    foreignKeys = [
        ForeignKey(
            entity = ProductEntity::class,
            parentColumns = ["id"],
            childColumns = ["productId"],
            onDelete = ForeignKey.CASCADE
        )],
    indices = [Index(value = ["productId"])]
)
data class CommentEntity(
    @PrimaryKey
    var id: Int? = null,
    var productId: Int? = null,
    var text: String? = null,
    var postedAt: Date? = null
) {
}