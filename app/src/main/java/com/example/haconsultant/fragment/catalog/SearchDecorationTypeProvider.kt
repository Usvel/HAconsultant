package com.example.haconsultant.fragment.catalog

import android.content.Context

interface SearchDecorationTypeProvider {
    fun getTypeProsition(position: Int, context: Context) : Boolean
}