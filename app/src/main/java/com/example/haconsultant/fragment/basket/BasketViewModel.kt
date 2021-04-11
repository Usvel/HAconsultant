package com.example.haconsultant.fragment.basket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.haconsultant.model.HomeData
import com.example.haconsultant.model.Product

class BasketViewModel : ViewModel() {

    val _basketList: MutableLiveData<MutableList<Product>> = MutableLiveData()

    val basketList: LiveData<MutableList<Product>> = _basketList

    init {
        _basketList.value = HomeData.getMutableProduct()
    }
}