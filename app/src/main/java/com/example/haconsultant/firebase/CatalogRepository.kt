package com.example.haconsultant.firebase

import com.example.haconsultant.firebase.api.CatalogApi
import com.example.haconsultant.model.Product
import io.reactivex.Single

class CatalogRepository(
    private val api: CatalogApi
) {
    fun loadProductr(): Single<List<Product>> {
        return api.product()
    }
    fun loadHomeNewsIteam() : List<Product> {
        return api.getHomeNewItems()
    }
}