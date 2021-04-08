package com.example.haconsultant.fragment.home

import android.content.Context

interface HomeDecorationTypeProvider {
    fun getTypeProsition(position: Int, context: Context) : Boolean
}