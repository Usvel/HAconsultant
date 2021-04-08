package com.example.haconsultant.fragment.home

import com.example.haconsultant.model.HomeCatalog
import com.example.haconsultant.model.Product

interface HomeFragmentInteractor {
    fun onOpenItem(product: Product)
    fun onOpenCatalog(homeCatalog: HomeCatalog)
    fun onOpenCameraQrCode()
    fun onOpenSerch()
}