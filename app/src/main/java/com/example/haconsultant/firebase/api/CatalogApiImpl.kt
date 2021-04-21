package com.example.haconsultant.firebase.api

import android.content.Context
import android.graphics.PostProcessor
import android.util.Log
import com.example.haconsultant.model.Product
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import io.reactivex.Single


class CatalogApiImpl(context: Context) : CatalogApi {

    var db = FirebaseFirestore.getInstance()
    override fun product(): Single<List<Product>> {

        db.collection("catalog").get().addOnSuccessListener { result ->
            for (document in result) {
                document.getDocumentReference("ref")?.addSnapshotListener { value, error ->
                    Log.d("Firebase", value?.data.toString())
                }
                //db.
                // Log.d("Firebase", "${document.id} => ${document.getDocumentReference("product")}")
            }
        }.addOnFailureListener { exception ->
            Log.w(
                "Frebase",
                "Error getting documents.",
                exception
            )
        }

        db.collection("catalog").document("phone").addSnapshotListener { value, error ->
            (value?.get("ref") as DocumentReference).addSnapshotListener { value, error ->
                if (value != null) {
                    Log.d("Firebase", value.data.toString())
                }
            }

        }


        return Single.just(
            listOf<Product>(
                Product(
                    name = "Смартфон Apple iPhone XR 128GB Black",
                    codeVendor = "2220001",
                    imageUrl = "https://static.eldorado.ru/photos/71/715/665/96/new_71566596_l_1605093913.jpeg",
                    prices = 2000,
                    evaluation = 4.3F,
                    sizeReviews = 23,
                    weight = 2.2F,
                    listImage = listOf(
                        "https://static.eldorado.ru/photos/71/715/665/78/new_71566578_l_1605089603.jpeg/resize/380x240/",
                        "https://static.eldorado.ru/photos/71/715/665/78/new_71566578_l_1605089623.jpeg/resize/380x240/",
                        "https://static.eldorado.ru/photos/71/715/665/78/new_71566578_l_1605089643.jpeg/resize/380x240/"
                    )
                ),
                Product(
                    name = "Игровая приставка Sony PlayStation 5",
                    codeVendor = "2220002",
                    imageUrl = "https://static.eldorado.ru/photos/71/715/399/84/new_71539984_l_1600344297.jpeg",
                    prices = 50000,
                    evaluation = 2.3F,
                    sizeReviews = 2,
                    weight = 5.2F,
                    listImage = listOf(
                        "https://img.mvideo.ru/Pdb/40073270b.jpg",
                        "https://img.mvideo.ru/Pdb/40073270b1.jpg",
                        "https://img.mvideo.ru/Pdb/40073270b2.jpg",
                        "https://img.mvideo.ru/Pdb/40073270b3.jpg"
                    )
                )
            )
        )
    }

    override fun getHomeNewItems(): List<Product> {
//        db.collection("home").get().addOnSuccessListener {
//            for (doc in it){
//                Log.d("Firebase", doc.id)
//            }
//        }
        var list: MutableList<Product> = mutableListOf()


        db.collection("home").document("new").get().addOnSuccessListener { document ->
            Log.d("RX", document.data?.size.toString())
            list = mutableListOf()
            document.data?.forEach { iteam ->
                Log.d("RX",iteam.value.toString())
                (iteam.value as DocumentReference).addSnapshotListener() { value, error ->
                    val product =
                        Product(
                            name = value?.getString("name").toString(),
                            codeVendor = value?.id.toString(),
                            imageUrl = value?.getString("imageUrl"),
                            prices = value?.getLong("prices").let {
                                it!!.toInt()
                            },
                            evaluation = value?.getLong("evaluation")!!.toFloat(),
                            sizeReviews = value?.getLong("sizeReviews")!!.toInt(),
                            weight = value?.getLong("weight")!!.toFloat(),
                            listImage = listOf()
                        )
                    list!!.add(product)
                    Log.d("Rx", list.toString())
                }
            }
        }
        return list
    }


    fun setProduct() {}
}