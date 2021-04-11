package com.example.haconsultant.fragment.basket

import com.example.haconsultant.model.Product

interface BasketRemoveListener {
    fun removeItem(product: Product, position: Int)
}