package com.example.haconsultant.fragment

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.haconsultant.R
import com.example.haconsultant.fragment.basket.BasketFragment
import com.example.haconsultant.fragment.catalog.CatalogFragment
import com.example.haconsultant.fragment.home.HomeFragment
import com.example.haconsultant.fragment.product.ProductFragment
import com.example.haconsultant.fragment.user.UserFragment
import com.example.haconsultant.model.HomeData
import java.util.*

class BackStackLiveData : ViewModel() {

    private val _queueFragmenHome: MutableLiveData<Stack<Fragment>> = MutableLiveData()
    private val _queueFragmenCatalog: MutableLiveData<Stack<Fragment>> = MutableLiveData()
    private val _queueFragmenBasket: MutableLiveData<Stack<Fragment>> = MutableLiveData()
    private val _queueFragmenUser: MutableLiveData<Stack<Fragment>> = MutableLiveData()
    private val _statusFragment: MutableLiveData<StatusFragment> = MutableLiveData()
    private val _statusCamera: MutableLiveData<StatusCamera> = MutableLiveData()

    //val queueFragmenHome: LiveData<Queue<Fragment>> = _queueFragmenHome
    val statusFragment: LiveData<StatusFragment> = _statusFragment
    val statusCamera: LiveData<StatusCamera> = _statusCamera

    init {
        this._queueFragmenHome.value = Stack()
        this._queueFragmenCatalog.value = Stack()
        this._queueFragmenBasket.value = Stack()
        this._queueFragmenUser.value = Stack()
        this._statusFragment.value = StatusFragment.Search
        this._statusCamera.value = StatusCamera.CameraOff
    }

    fun setStatusCamera(statusCamera: StatusCamera) {
        _statusCamera.value = statusCamera
    }

    fun setStatus(statusFragment: StatusFragment) {
        _statusFragment.value = statusFragment
    }

    fun clearQueueFragment() {
        when (statusFragment.value) {
            StatusFragment.Search -> {
                val startFragment = _queueFragmenHome.value?.get(0)
                _queueFragmenHome.value?.clear()
                _queueFragmenHome.value?.push(startFragment)
            }
            StatusFragment.Catalog -> {
                val startFragment = _queueFragmenCatalog.value?.get(0)
                _queueFragmenCatalog.value?.clear()
                _queueFragmenCatalog.value?.push(startFragment)
            }
            StatusFragment.Basket -> {
                val startFragment = _queueFragmenBasket.value?.get(0)
                _queueFragmenBasket.value?.clear()
                _queueFragmenBasket.value?.push(startFragment)
            }
            StatusFragment.User -> {
                val startFragment = _queueFragmenUser.value?.get(0)
                _queueFragmenUser.value?.clear()
                _queueFragmenUser.value?.push(startFragment)
            }
        }
    }

    fun addQueueFragment(fragment: Fragment) {
        when (statusFragment.value) {
            StatusFragment.Search -> {
                _queueFragmenHome.value?.push(fragment)
            }
            StatusFragment.Catalog -> {
                _queueFragmenCatalog.value?.push(fragment)
            }
            StatusFragment.Basket -> {
                _queueFragmenBasket.value?.push(fragment)
            }
            StatusFragment.User -> {
                _queueFragmenUser.value?.push(fragment)
            }
        }
    }

    fun removeQueueFragment(): Fragment? {
        when (statusFragment.value) {
            StatusFragment.Search -> {
                return _queueFragmenHome.value?.pop()
            }
            StatusFragment.Catalog -> {
                return _queueFragmenCatalog.value?.pop()
            }
            StatusFragment.Basket -> {
                return _queueFragmenBasket.value?.pop()
            }
            StatusFragment.User -> {
                return _queueFragmenUser.value?.pop()
            }
        }
        return null
    }

    fun lastQeueFragment(): Fragment? {
        when (statusFragment.value) {
            StatusFragment.Search -> {
                return _queueFragmenHome.value?.lastOrNull()
            }
            StatusFragment.Catalog -> {
                return _queueFragmenCatalog.value?.lastOrNull()
            }
            StatusFragment.Basket -> {
                return _queueFragmenBasket.value?.lastOrNull()
            }
            StatusFragment.User -> {
                return _queueFragmenUser.value?.lastOrNull()
            }
        }
        return ProductFragment()
    }

    fun startQueueFragment() {
        _queueFragmenHome.value?.push(HomeFragment())
        _queueFragmenCatalog.value?.push(CatalogFragment())
        _queueFragmenBasket.value?.push(BasketFragment())
        _queueFragmenUser.value?.push(UserFragment())
    }
}

enum class StatusCamera() {
    CameraOn,
    CameraOff
}

enum class StatusFragment() {
    Search,
    Catalog,
    Basket,
    User
}