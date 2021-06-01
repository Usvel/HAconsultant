package com.example.haconsultant.model

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.firestore.DocumentReference
import java.io.Serializable

data class Product(
    val name: String,
    val codeVendor: String,
    val imageUrl: String?,
    val prices: Int,
    val evaluation: Float,
    val sizeReviews: Int,
    val weight: Float,
    val listImage: List<String>,
    val description: String,
    val listProduct: List<String>,
    val manufacturer: String,
    val characteristics: Map<String, Any>
) : Serializable