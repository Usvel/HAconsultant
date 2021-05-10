package com.example.haconsultant.fragment.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.haconsultant.firebase.api.TypeSort
import com.example.haconsultant.model.Feature
import com.example.haconsultant.model.HomeData
import com.example.haconsultant.model.Product

class SearhViewModel : ViewModel() {
    private val _searchSort: MutableLiveData<TypeSort> = MutableLiveData()
    private val _searchList: MutableLiveData<List<Product>> = MutableLiveData()
    private val _statePlace: MutableLiveData<StatePlace> = MutableLiveData()
    val feature: MutableLiveData<Feature> = MutableLiveData()
    val mapFilter: MutableLiveData<Map<String, Any>> = MutableLiveData()

    val searchList: LiveData<List<Product>> = _searchList
    val statePlace: LiveData<StatePlace> = _statePlace
    val searhSort: LiveData<TypeSort> = _searchSort

    init {
        _searchList.value = listOf()
    }

    fun setStatePlace(statePlace: StatePlace) {
        _statePlace.value = statePlace
    }

    fun setList(list: List<Product>) {
        _searchList.value = list
    }

    fun setSort(searchSort: TypeSort){
        _searchSort.value = searchSort
    }
}