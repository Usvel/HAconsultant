package com.example.haconsultant.fragment.basket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.haconsultant.model.BasketItem
import com.example.haconsultant.model.HomeData
import com.example.haconsultant.model.Product

class BasketViewModel : ViewModel() {

    private val _basketList: MutableLiveData<MutableList<BasketItem>> = MutableLiveData()
    private val _price: MutableLiveData<Int> = MutableLiveData()
    private val _weight: MutableLiveData<Float> = MutableLiveData()

    val basketList: LiveData<MutableList<BasketItem>> = _basketList
    val price: LiveData<Int> = _price
    val weight: LiveData<Float> = _weight

    init {
        _basketList.value = HomeData.getbasketItem()
        _price.value = 0
        _weight.value = 0F
    }

    fun itmeZero() {
        _price.value = 0
        _weight.value = 0F
    }

    fun minusItem(item: Product) {
        _price.value = _price.value?.minus(item.prices)
        _weight.value = _weight.value?.minus(item.weight)
    }

    fun setItemsValue(price: Int, weight: Float) {
        _price.value = price
        _weight.value = weight
    }
}