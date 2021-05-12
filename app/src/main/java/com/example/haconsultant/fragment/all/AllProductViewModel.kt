package com.example.haconsultant.fragment.all

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.haconsultant.model.Product

class AllProductViewModel : ViewModel() {
    private val _searchList: MutableLiveData<List<Product>> = MutableLiveData()

    val searchList: LiveData<List<Product>> = _searchList

    init {
        _searchList.value = listOf()
    }

    fun setList(list: List<Product>) {
        _searchList.value = list
    }
}