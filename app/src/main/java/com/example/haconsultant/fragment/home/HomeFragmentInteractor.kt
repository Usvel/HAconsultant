package com.example.haconsultant.fragment.home

import com.example.haconsultant.model.HomeCatalog
import com.example.haconsultant.model.Product

interface HomeFragmentInteractor {
    fun onHomeOpenItem(product: Product)
    fun onHomeOpenCatalog(homeCatalog: HomeCatalog)
    fun onHomeOpenCameraQrCode()
    fun onHomeOpenSerch()
}