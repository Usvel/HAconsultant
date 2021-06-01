package com.example.haconsultant.fragment.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {

    private val _name: MutableLiveData<String> = MutableLiveData()
    private val _id: MutableLiveData<String> = MutableLiveData()

    val name: LiveData<String> = _name
    val id: LiveData<String> = _id

    init {
        this._name.value = "Отсканируйте Qr-код"
        this._id.value = "00000000"
    }

    fun setUser(name: String, id: String) {
        _name.value = name
        _id.value = id
    }
}