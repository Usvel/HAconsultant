package com.example.haconsultant.fragment.basket

import com.example.haconsultant.model.BasketItem

interface BasketRemoveListener {
    fun removeItem(basketItem: BasketItem, position: Int)
}