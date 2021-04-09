package com.example.haconsultant.fragment.catalog

import com.example.haconsultant.model.HomeCatalog
import com.example.haconsultant.model.Product

interface SearchFragmentInteractor {
    fun onSearchOpenItem(product: Product)
    fun onSearchBack()
}