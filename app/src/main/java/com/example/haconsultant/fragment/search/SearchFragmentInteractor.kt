package com.example.haconsultant.fragment.search

import com.example.haconsultant.model.Product

interface SearchFragmentInteractor {
    fun onSearchOpenItem(product: Product)
    fun onSearchBack()
}