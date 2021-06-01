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
    private val _status: MutableLiveData<BasketStatus> = MutableLiveData()
    private val _sizeProduct: MutableLiveData<Int> = MutableLiveData()

    val basketList: LiveData<MutableList<BasketItem>> = _basketList
    val price: LiveData<Int> = _price
    val weight: LiveData<Float> = _weight
    val status: LiveData<BasketStatus> = _status
    val sizeProduct: LiveData<Int> = _sizeProduct

    init {
        _basketList.value = mutableListOf()
        _price.value = 0
        _weight.value = 0F
        _status.value = BasketStatus.Clear
        _sizeProduct.value = 0
    }

    fun addProduct(product: Product) {
        _basketList.value?.add(BasketItem(product, 1))
    }

    fun itmeZero() {
        _price.value = 0
        _weight.value = 0F
        _sizeProduct.value = 0
    }

    fun basketToZero() {
        setStatus(BasketStatus.Clear)
        itmeZero()
        _basketList.value = mutableListOf()
    }

    fun minusItem(item: Product) {
        _price.value = _price.value?.minus(item.prices)
        _weight.value = _weight.value?.minus(item.weight)
        _sizeProduct.value = sizeProduct.value?.minus(1)
    }

    fun setItemsValue(price: Int, weight: Float, sizeProduct: Int) {
        _price.value = price
        _weight.value = weight
        _sizeProduct.value = sizeProduct
    }

    fun setStatus(status: BasketStatus) {
        _status.value = status
    }
}

enum class BasketStatus() {
    Clear,
    Filled
}