package com.example.haconsultant.fragment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.haconsultant.model.HomeCatalog
import com.example.haconsultant.model.HomeData
import com.example.haconsultant.model.Product

class HomeViewModel : ViewModel() {

    private val _newList: MutableLiveData<List<Product>> = MutableLiveData()
    private val __lastList: MutableLiveData<List<Product>> = MutableLiveData()
    private val _catalogList: MutableLiveData<List<HomeCatalog>> = MutableLiveData()

    private val _statusNewList: MutableLiveData<Status> = MutableLiveData()
    val statusNewList: LiveData<Status> = _statusNewList

    val newList: LiveData<List<Product>> = _newList
    val lastList: LiveData<List<Product>> = __lastList
    val catalogList: LiveData<List<HomeCatalog>> = _catalogList

    init {
        _newList.value = listOf()
        __lastList.value = HomeData.getProduct()
        _catalogList.value = HomeData.getHomeCatalog()
        _statusNewList.value = Status.Loading
    }

    fun setNewList(list: List<Product>) {
        _newList.value = list
    }

    fun setStatusNew(status: Status){
        _statusNewList.value = status
    }
}


enum class Status {
    Loading,
    Success,
    Failure
}