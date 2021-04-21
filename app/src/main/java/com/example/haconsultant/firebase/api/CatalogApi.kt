package com.example.haconsultant.firebase.api

import com.example.haconsultant.model.Product
import io.reactivex.Single

interface CatalogApi {
    fun product(): Single<List<Product>>
    fun getHomeNewItems(): List<Product>
}