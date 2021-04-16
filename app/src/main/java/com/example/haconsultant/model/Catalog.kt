package com.example.haconsultant.model

import android.os.Parcel
import android.os.Parcelable

data class Catalog(
    val name: String,
    val listCatalog: List<Catalog>?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.createTypedArrayList(CREATOR)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeTypedList(listCatalog)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Catalog> {
        override fun createFromParcel(parcel: Parcel): Catalog {
            return Catalog(parcel)
        }

        override fun newArray(size: Int): Array<Catalog?> {
            return arrayOfNulls(size)
        }
    }
}
