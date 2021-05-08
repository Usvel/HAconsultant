package com.example.haconsultant.firebase.api

import com.example.haconsultant.model.CatalogFirestore
import com.example.haconsultant.model.Product
import io.reactivex.Flowable
import io.reactivex.Single

interface CatalogApi {
    fun product(): Single<List<Product>>
    fun getHomeNewItems(): Flowable<Product>?
    fun openProduct(codeVendor: String): Single<Product>
    fun getListProduct(list: List<String>): Flowable<Product>?
    fun catalogStart(): Single<CatalogFirestore>
}