package com.example.haconsultant.fragment.product

import com.example.haconsultant.model.Product
import io.reactivex.Flowable

interface ProductFragmentInteractor {
    fun addShopping(product: Product)
    fun openAllDescription(text: String)
    fun openAllFeature(text: String)
    fun openAllSubjects()
    fun onProductBack()
    fun onProductOpenItem(product: Product)
    fun statusProductr(product: Product): Boolean
    fun loadListProduct(list: List<String>): Flowable<Product>
}