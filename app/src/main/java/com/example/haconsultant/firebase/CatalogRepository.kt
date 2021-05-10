package com.example.haconsultant.firebase

import com.example.haconsultant.firebase.api.CatalogApi
import com.example.haconsultant.firebase.api.TypeSort
import com.example.haconsultant.model.CatalogFirestore
import com.example.haconsultant.model.Product
import io.reactivex.Flowable
import io.reactivex.Single

class CatalogRepository(
    private val api: CatalogApi
) {
    fun loadProductr(): Single<List<Product>> {
        return api.product()
    }

    fun loadHomeNewsIteam(): Flowable<Product> {
        return api.getHomeNewItems()!!
    }

    fun openCodeProduct(codeVendor: String): Single<Product> {
        return api.openProduct(codeVendor)
    }

    fun loadListProduct(list: List<String>): Flowable<Product> {
        return api.getListProduct(list)!!
    }

    fun startCatalog(): Single<CatalogFirestore> {
        return api.catalogStart()
    }

    fun nextCatalog(
        document: String,
        name: String,
        nameBD: String,
        feature: MutableMap<String, Any>
    ): Single<CatalogFirestore> {
        return api.catalogNext(document, name, nameBD, feature)
    }

    fun feature(
        priceMin: Int?,
        priceMax: Int?,
        manufacturer: String?,
        typeSort: TypeSort?,
        map: Map<String, Any>?,
        nameCatalog: String?
    ): Single<List<Product>> {
        return api.feature(priceMin, priceMax, manufacturer, typeSort, map, nameCatalog)
    }
}