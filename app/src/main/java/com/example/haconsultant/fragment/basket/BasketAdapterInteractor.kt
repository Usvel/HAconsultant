package com.example.haconsultant.fragment.basket

import com.example.haconsultant.model.BasketItem

interface BasketAdapterInteractor {
    fun sizeInZero()
    fun minusPrice(item:BasketItem)
}