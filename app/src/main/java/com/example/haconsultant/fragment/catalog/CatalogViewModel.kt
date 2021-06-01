package com.example.haconsultant.fragment.catalog

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.haconsultant.model.CatalogFirestore
import java.util.ArrayList

class CatalogViewModel : ViewModel() {
    val _stackCatalogFirestore: MutableLiveData<MutableList<CatalogFirestore>> =
        MutableLiveData()
    private val _catalogFirestore: MutableLiveData<CatalogFirestore> = MutableLiveData()


    val catalogFirestore = _catalogFirestore

    init {
        this._stackCatalogFirestore.value = ArrayList<CatalogFirestore>()
    }

    fun setCatalog(catalog: CatalogFirestore) {
        _catalogFirestore.value?.let { _stackCatalogFirestore.value?.add(it) }
        _catalogFirestore.value = catalog
    }

    fun backCatalog() {
        _catalogFirestore.value = _stackCatalogFirestore.value?.last()
        _stackCatalogFirestore.value?.removeLast()
    }

    fun setStartCatalog() {
        while (_stackCatalogFirestore.value?.size != 0) {
            backCatalog()
        }
    }

}