package com.example.haconsultant.fragment.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.haconsultant.model.HomeData
import com.example.haconsultant.model.Product

class ProductViewModel : ViewModel() {

    private val _product: MutableLiveData<Product> = MutableLiveData()
    val product: LiveData<Product> = _product

    init {
        _product.value = HomeData.getProduct().component1()
    }

    fun setPost(product: Product) {
        _product.value = product
    }
}