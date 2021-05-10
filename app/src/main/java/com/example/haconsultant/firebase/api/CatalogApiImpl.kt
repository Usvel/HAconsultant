package com.example.haconsultant.firebase.api

import android.content.Context
import android.util.Log
import com.example.haconsultant.model.CatalogFirestore
import com.example.haconsultant.model.Product
import com.google.firebase.firestore.*
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.SingleEmitter
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class CatalogApiImpl(var context: Context) : CatalogApi {

    var db = FirebaseFirestore.getInstance()

    override fun product(): Single<List<Product>> {
        //feature(priceMin = 3000)
        val map = mapOf<String, Any>(Pair("Цвет", "Голубой"))//, Pair("NFC", true))
        //db.collection("catalog").document("phone").collection()
        val order = mutableMapOf<String, Any>(
            Pair("Цвет", listOf("Голубой", "Синий", "Красный")),
            //Pair("NFC", listOf(true, false))
        )
//        feature(
////            order = mapOf(Pair("Цвет", "да")),
//            nameCatalog = "Телефон",
//            map = map,
//            typeSort = TypeSort("evaluation", "desc")
//        ).subscribe({
//
//        }, {
//
//        })

        db.collection("catalog").get().addOnSuccessListener {
//            catalogNext(
//                (it.documents.get(0).reference).path,
//                it.documents.get(0).getString("name")!!,
//                it.documents.get(0).id,
//
//            )
        }
        return Single.just(
            listOf()
        )
    }

    override fun catalogStart(): Single<CatalogFirestore> {
        val catalog =
            CatalogFirestore(
                name = "Каталог",
                db.collection("catalog").path,
                null,
                null,
                null,
            )
        return Single.create { emmiter ->
            db.collection("catalog").get().addOnSuccessListener {
                val pathList = arrayListOf<String>()
                val nameList = arrayListOf<String>()
                val nameBD = arrayListOf<String>()
                val feature = arrayListOf<Map<String, Any>>()

                it.documents.forEach { document ->

                    pathList.add(document.reference.path)
                    nameList.add(document.getString("name")!!)

                    val data = document.data
                    data?.remove("name")

                    nameBD.add(document.id)
                    data?.let {
                        feature.add(it)
                    }

                }
                catalog.pashlist = pathList
                catalog.listName = nameList
                catalog.nameBd = nameBD
                catalog.listFeature = feature
                Log.d("catalog", catalog.toString())
                emmiter.onSuccess(catalog)
            }
        }
    }

    override fun catalogNext(
        document: String,
        name: String,
        nameBD: String,
        feature: MutableMap<String, Any>
    ): Single<CatalogFirestore> {
        val catalog = CatalogFirestore(name = name, document, null, null, null, feature)
        return Single.create { emmiter ->
            db.document(document).collection(nameBD).get().addOnSuccessListener {
                val pathList = arrayListOf<String>()
                val nameList = arrayListOf<String>()
                val nameBD = arrayListOf<String>()
                val listFeature = arrayListOf<Map<String, Any>>()
                it.documents.forEach { document ->
                    val data = document.data
                    pathList.add(document.reference.path)
                    nameList.add(document.getString("name")!!)
                    nameBD.add(document.id)

                    data?.remove("name")
                    data?.let { it -> listFeature.add(it) }


//                catalog(
//                    catalog.pashlist!!.get(0),
//                    catalog.listName!!.get(0),
//                    catalog.nameBd!!.get(0)
//                )
                }

                catalog.pashlist = pathList
                catalog.listName = nameList
                catalog.nameBd = nameBD
                catalog.listFeature = listFeature
                Log.d("catalog", catalog.toString())
                emmiter.onSuccess(catalog)
            }
        }
    }

    override fun feature(
        priceMin: Int?,
        priceMax: Int?,
        manufacturer: String?,
        typeSort: TypeSort?,
        map: Map<String, Any>?,
        nameCatalog: String?
    ): Single<List<Product>> {
        var zapros = db.collection("product") as Query

        if (priceMin != null) {
            Log.d("Catalog-123", priceMin.toString())
            zapros = zapros.whereGreaterThanOrEqualTo("prices", priceMin)
        }
        if (priceMax != null) {
            Log.d("Catalog-123", priceMax.toString())
            zapros = zapros.whereLessThanOrEqualTo("prices", priceMax)
        }

        if (nameCatalog != null) {
            Log.d("Catalog-123", nameCatalog.toString())
            zapros = zapros.whereArrayContains("Каталог", nameCatalog)
        }

        if (manufacturer != null) {
            Log.d("Catalog-123", manufacturer.toString())
            zapros = zapros.whereEqualTo("manufacturer", manufacturer)
        }

        map?.forEach {
            Log.d("Catalog-123", it.key + " " + it.value)
            zapros = zapros.whereEqualTo(it.key, it.value)
        }

        if (typeSort != null) {
            Log.d("Catalog-123", typeSort.toString())
            if (typeSort.value == "desc") {
                zapros = zapros.orderBy(typeSort.key, Query.Direction.DESCENDING)
            } else {
                zapros = zapros.orderBy(typeSort.key, Query.Direction.ASCENDING)
            }
        }

        return Single.create { emmiter ->
            Log.d("Searsh", "вошел")
            zapros.get().addOnSuccessListener {
                val listProduct = arrayListOf<Product>()
                it.documents.forEach {
                    listProduct.add(convertToProduct(it))
                }
                Log.d("Searsh", listProduct.toString())
                emmiter.onSuccess(listProduct)
            }
        }
    }

    override fun getHomeNewItems(): Flowable<Product>? {
        return Single.create { emmiter: SingleEmitter<DocumentSnapshot> ->
            db.collection("home").document("new").get().addOnSuccessListener { document ->
                if (document != null) {
                    emmiter.onSuccess(document)
                } else {
                    emmiter.onError(RuntimeException("Ошибка загрузки новых товаров"))
                }
            }
        }.flatMapPublisher { document ->
            val responses: MutableList<Single<Product>> = ArrayList()
            document.data?.forEach { iteam ->
                responses.add(Single.create { emmiter: SingleEmitter<Product> ->
                    (iteam.value as DocumentReference).addSnapshotListener() { value, error ->
                        if ((error == null) && (value?.data != null)) {
                            val product = convertToProduct(value)
                            getListProduct(product.listProduct)
                            emmiter.onSuccess(product)
                        } else {
                            Log.d("RX-e", error.toString())

                        }
                    }
                })
            }
            Single.concat(responses)
        }
    }

    override fun getListProduct(list: List<String>): Flowable<Product>? {
        return Single.just(list).flatMapPublisher { listadd ->
            val responses: MutableList<Single<Product>> = ArrayList()
            listadd.forEach { iteam ->
                responses.add(Single.create { emmiter: SingleEmitter<Product> ->
                    db.document(iteam).addSnapshotListener() { value, error ->
                        if ((error == null) && (value?.data != null)) {
                            val product = convertToProduct(value)
                            emmiter.onSuccess(product)
                        } else {
                            Log.d("RX-e", error.toString())

                        }
                    }
                })
            }
            Single.concat(responses)
        }
    }

    override fun openProduct(codeVendor: String): Single<Product> {
        return Single.create { emitter ->
            db.collection("product").document(codeVendor)
                .addSnapshotListener { value, error ->
                    if ((error == null) && (value?.data != null)) {
                        val product = convertToProduct(value)
                        emitter.onSuccess(product)
                    } else {
                        emitter.onError(java.lang.RuntimeException("Не найден данный продукт $codeVendor"))
                    }
                }
        }
    }

    private fun convertToProduct(value: DocumentSnapshot): Product {
        val listProduct: MutableList<String> = ArrayList()
        (value.get("listProduct") as List<DocumentReference>).forEach {
            listProduct.add(it.path)
        }

        val data = value.data!!

        val name = value.getString("name")!!
        val codeVendor = value.id
        val imageUrl = value.getString("imageUrl")
        val prices = value.getLong("prices").let {
            it!!.toInt()
        }
        val evaluation = value.getDouble("evaluation")!!.toFloat()
        val sizeReviews = value.getLong("sizeReviews")!!.toInt()
        val weight = value.getDouble("weight")!!.toFloat()
        val listImage = value.get("listImage") as List<String>
        val description = value.getString("description")!!
        val manufacturer = value.getString("manufacturer")!!

        data.remove("name")
        data.remove("codeVendor")
        data.remove("imageUrl")
        data.remove("prices")
        data.remove("evaluation")
        data.remove("sizeReviews")
        data.remove("weight")
        data.remove("listImage")
        data.remove("description")
        data.remove("listProduct")
        data.remove("manufacturer")

        Log.d("Data", data.toString())
        val product = Product(
            name = name,
            codeVendor = codeVendor,
            imageUrl = imageUrl,
            prices = prices,
            evaluation = evaluation,
            sizeReviews = sizeReviews,
            weight = weight,
            listImage = listImage,
            description = description,
            listProduct = listProduct,
            manufacturer = manufacturer,
            characteristics = data as HashMap<String, Any>
        )
        return product
    }
}

data class TypeSort(
    val key: String,
    val value: String
)


