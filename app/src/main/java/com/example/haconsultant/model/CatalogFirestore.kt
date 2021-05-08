package com.example.haconsultant.model

data class CatalogFirestore(
    val name: String,
    val reference: String,
    var pashlist: List<String>?,
    var listName: List<String>?,
    var nameBd: List<String>?
)