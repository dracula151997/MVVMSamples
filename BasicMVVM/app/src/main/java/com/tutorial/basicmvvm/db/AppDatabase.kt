package com.tutorial.basicmvvm.db

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.tutorial.basicmvvm.AppExecutors
import com.tutorial.basicmvvm.db.converter.DateConverter
import com.tutorial.basicmvvm.db.dao.CommentDao
import com.tutorial.basicmvvm.db.dao.ProductDao
import com.tutorial.basicmvvm.db.entity.CommentEntity
import com.tutorial.basicmvvm.db.entity.ProductEntity
import com.tutorial.basicmvvm.db.entity.ProductFtsEntity

@Database(
    entities = [ProductEntity::class, ProductFtsEntity::class, CommentEntity::class],
    version = 1
)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun commentDao(): CommentDao
    private val mIsDatabaseCreated = MutableLiveData<Boolean>()

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, executors: AppExecutors): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = buildDatabase(context.applicationContext, executors)
                instance.updateDatabaseCreated(context.applicationContext)
                INSTANCE = instance
                instance
            }
        }

        private fun buildDatabase(
            applicationContext: Context,
            executors: AppExecutors
        ): AppDatabase {
            return Room.databaseBuilder(applicationContext, AppDatabase::class.java, "app_database")
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        executors.diskIO?.execute {
                            addDelay()
                            val database = getDatabase(applicationContext, executors)
                            val products = DataGenerator.generateProducts()
                            val comments = DataGenerator.generateCommentsForProducts(products)
                            insertData(database, products, comments)
                            database.setDatabaseCreated()

                        }
                    }
                })
                .build()
        }

        private fun insertData(
            database: AppDatabase,
            products: List<ProductEntity>,
            comments: List<CommentEntity>
        ) {
            database.runInTransaction {
                database.productDao().insertAll(products)
                database.commentDao().insertAll(comments)
            }
        }

        private fun addDelay() {
            Thread.sleep(4000)
        }
    }

    private fun updateDatabaseCreated(applicationContext: Context) {
        if (applicationContext.getDatabasePath("app_database").exists())
            setDatabaseCreated()

    }

    private fun setDatabaseCreated() {
        mIsDatabaseCreated.postValue(true)
    }

    val isDatabaseCreated : LiveData<Boolean> = mIsDatabaseCreated


}