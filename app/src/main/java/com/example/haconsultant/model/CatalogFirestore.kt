package com.example.haconsultant.model

data class CatalogFirestore(
    val name: String,
    val reference: String,
    var pashlist: List<String>?,
    var listName: List<String>?,
    var nameBd: List<String>?,
    var feature: MutableMap<String, Any>? = mutableMapOf(),
    var listFeature: MutableList<Map<String, Any>>? = null
)