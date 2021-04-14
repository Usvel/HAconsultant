package com.example.haconsultant.model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class Product(
    val name: String,
    val codeVendor : String,
    val imageUrl:String?,
    val prices: Int,
    val evaluation: Float,
    val sizeReviews : Int,
    val weight : Float
) : Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString(),
        parcel.readInt(),
        parcel.readFloat(),
        parcel.readInt(),
        parcel.readFloat()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(codeVendor)
        parcel.writeString(imageUrl)
        parcel.writeInt(prices)
        parcel.writeFloat(evaluation)
        parcel.writeInt(sizeReviews)
        parcel.writeFloat(weight)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }
}