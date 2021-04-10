package com.tutorial.basicmvvm.db

import com.tutorial.basicmvvm.db.entity.CommentEntity
import com.tutorial.basicmvvm.db.entity.ProductEntity
import java.util.*
import java.util.concurrent.TimeUnit

object DataGenerator {

    private val FIRST = arrayOf(
        "Special edition", "New", "Cheap", "Quality", "Used"
    )
    private val SECOND = arrayOf(
        "Three-headed Monkey", "Rubber Chicken", "Pint of Grog", "Monocle"
    )
    private val DESCRIPTION = arrayOf(
        "is finally here", "is recommended by Stan S. Stanman",
        "is the best sold product on Mêlée Island", "is \uD83D\uDCAF", "is ❤️", "is fine"
    )
    private val COMMENTS = arrayOf(
        "Comment 1", "Comment 2", "Comment 3", "Comment 4", "Comment 5", "Comment 6"
    )


    fun generateProducts(): List<ProductEntity> {
        val products: MutableList<ProductEntity> =
            ArrayList<ProductEntity>(FIRST.size * SECOND.size)
        val rnd = Random()
        for (i in FIRST.indices) {
            for (j in SECOND.indices) {
                val product = ProductEntity()
                product.name = (FIRST[i] + " " + SECOND[j])
                product.description = product.name() + " " + DESCRIPTION[j]
                product.price = rnd.nextInt(240)
                product.id = FIRST.size * i + j + 1
                products.add(product)
            }
        }
        return products
    }

    fun generateCommentsForProducts(
        products: List<ProductEntity>
    ): List<CommentEntity> {
        val comments: MutableList<CommentEntity> = ArrayList<CommentEntity>()
        val rnd = Random()
        for (product in products) {
            val commentsNumber = rnd.nextInt(5) + 1
            for (i in 0 until commentsNumber) {
                val comment = CommentEntity()
                comment.productId = product.id
                comment.text = COMMENTS[i] + " for " + product.name()
                comment.postedAt =
                    Date(
                        System.currentTimeMillis()
                                - TimeUnit.DAYS.toMillis((commentsNumber - i).toLong()) + TimeUnit.HOURS.toMillis(
                            i.toLong()
                        )
                    )

                comments.add(comment)
            }
        }
        return comments
    }
}