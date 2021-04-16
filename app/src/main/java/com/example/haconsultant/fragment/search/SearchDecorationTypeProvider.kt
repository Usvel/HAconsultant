package com.example.haconsultant.fragment.search

import android.content.Context

interface SearchDecorationTypeProvider {
    fun getTypeProsition(position: Int, context: Context) : Boolean
}