package com.example.haconsultant.fragment.product

import com.example.haconsultant.model.Product

interface ProductFragmentInteractor {
    fun addShopping(product: Product)
    fun openAllDescription()
    fun openAllFeature()
    fun openAllSubjects()
    fun onProductBack()
    fun onProductOpenItem(product: Product)
    fun statusProductr(product: Product): Boolean
}