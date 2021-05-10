package com.example.haconsultant.firebase.api

import com.example.haconsultant.model.CatalogFirestore
import com.example.haconsultant.model.Product
import com.google.android.gms.common.Feature
import io.reactivex.Flowable
import io.reactivex.Single

interface CatalogApi {
    fun product(): Single<List<Product>>
    fun getHomeNewItems(): Flowable<Product>?
    fun openProduct(codeVendor: String): Single<Product>
    fun getListProduct(list: List<String>): Flowable<Product>?
    fun catalogStart(): Single<CatalogFirestore>
    fun catalogNext(
        document: String,
        name: String,
        nameBD: String,
        feature: MutableMap<String, Any>
    ): Single<CatalogFirestore>

    fun feature(
        priceMin: Int? = null,
        priceMax: Int? = null,
        manufacturer: String? = null,
        typeSort: TypeSort? = null,
        map: Map<String, Any>? = null,
        nameCatalog: String? = null
    ): Single<List<Product>>
}