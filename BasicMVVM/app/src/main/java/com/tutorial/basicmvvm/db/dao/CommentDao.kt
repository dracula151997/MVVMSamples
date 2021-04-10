package com.tutorial.basicmvvm.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tutorial.basicmvvm.db.entity.CommentEntity

@Dao
interface CommentDao {
    @Query("SELECT * FROM comments")
    fun getAllComments(): LiveData<List<CommentEntity>>

    @Query("SELECT * FROM comments WHERE id= :commentId")
    fun getComment(commentId: Int): LiveData<CommentEntity>

    @Query("SELECT * FROM comments WHERE productId= :productId")
    fun getProductComments(productId: Int): LiveData<List<CommentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(comments: List<CommentEntity>)
}