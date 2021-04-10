package com.tutorial.basicmvvm.model

interface Product {
    fun id() : Int
    fun name() : String
    fun description() : String
    fun price() : Int
}