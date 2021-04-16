package com.example.haconsultant.fragment.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.haconsultant.model.HomeData
import com.example.haconsultant.model.Product

class SearhViewModel : ViewModel() {

    private val _searchList: MutableLiveData<List<Product>> = MutableLiveData()

    val searchList: LiveData<List<Product>> = _searchList

    init {
        _searchList.value = HomeData.getProduct()
    }
}