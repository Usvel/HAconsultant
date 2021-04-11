package com.example.haconsultant.model

data class Product(
    val name: String,
    val codeVendor : String,
    val imageUrl:String?,
    val prices: Int,
    val evaluation: Float,
    val sizeReviews : Int,
    val weight : Float
)