package com.example.haconsultant.fragment.basket

import com.example.haconsultant.model.Product

interface BasketFragmentInteractor {
    fun onBasketOpenItem(product: Product)
    fun onBasketCheckout()
}