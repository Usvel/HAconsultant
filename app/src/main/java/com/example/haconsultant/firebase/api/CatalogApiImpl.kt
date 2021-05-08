package com.example.haconsultant.firebase.api

import android.content.Context
import android.util.Log
import com.example.haconsultant.model.CatalogFirestore
import com.example.haconsultant.model.Product
import com.google.firebase.firestore.*
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.SingleEmitter


class CatalogApiImpl(var context: Context) : CatalogApi {

    var db = FirebaseFirestore.getInstance()

    override fun product(): Single<List<Product>> {
        feature(priceMin = 3000)

        //db.collection("catalog").document("phone").collection()

        db.collection("catalog").get().addOnSuccessListener {
            catalogNext(
                (it.documents.get(0).reference).path,
                it.documents.get(0).getString("name")!!,
                it.documents.get(0).id
            )
        }
        return Single.just(
            listOf()
        )
    }

    override fun catalogStart(): Single<CatalogFirestore> {
        val catalog =
            CatalogFirestore(name = "Каталог", db.collection("catalog").path, null, null, null)
        return Single.create { emmiter ->
            db.collection("catalog").get().addOnSuccessListener {
                it.documents.forEach { document ->
                    val pathList = arrayListOf<String>()
                    val nameList = arrayListOf<String>()
                    val nameBD = arrayListOf<String>()

                    pathList.add(document.reference.path)
                    nameList.add(document.getString("name")!!)
                    nameBD.add(document.id)

                    catalog.pashlist = pathList
                    catalog.listName = nameList
                    catalog.nameBd = nameBD

                    Log.d("catalog", catalog.toString())
                    emmiter.onSuccess(catalog)
                }
            }
        }
    }

    private fun catalogNext(
        document: String,
        name: String,
        nameBD: String
    ): Single<CatalogFirestore> {
        val catalog = CatalogFirestore(name = name, document, null, null, null)
        return Single.create { emmiter ->
            db.document(document).collection(nameBD).get().addOnSuccessListener {

                it.documents.forEach { document ->
                    val pathList = arrayListOf<String>()
                    val nameList = arrayListOf<String>()
                    val nameBD = arrayListOf<String>()

                    pathList.add(document.reference.path)
                    nameList.add(document.getString("name")!!)
                    nameBD.add(document.id)

                    catalog.pashlist = pathList
                    catalog.listName = nameList
                    catalog.nameBd = nameBD

                    emmiter.onSuccess(catalog)
//                catalog(
//                    catalog.pashlist!!.get(0),
//                    catalog.listName!!.get(0),
//                    catalog.nameBd!!.get(0)
//                )
                }
            }
        }
    }

    private fun feature(
        priceMin: Int? = null,
        priceMax: Int? = null,
        manufacturer: String? = null,
        map: Map<String, String>? = null
    ) {
        var zapros = db.collection("product") as Query
        if (priceMin != null) {
            zapros = zapros.whereGreaterThanOrEqualTo("prices", priceMin)
        }
        if (priceMax != null) {
            zapros = zapros.whereLessThanOrEqualTo("prices", priceMax)
        }
        if (manufacturer != null) {
            zapros = zapros.whereEqualTo("manufacturer", manufacturer)
        }

        map?.forEach {
            zapros = zapros.whereEqualTo(it.key, it.value)
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