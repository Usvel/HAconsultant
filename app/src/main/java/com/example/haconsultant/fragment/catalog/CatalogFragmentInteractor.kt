package com.example.haconsultant.fragment.catalog

import com.example.haconsultant.model.Catalog

interface CatalogFragmentInteractor {
    fun onCatalogOpenNext(catalog: String)
    fun onCatalogBack()
}