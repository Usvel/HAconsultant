package com.example.haconsultant.model

import com.example.haconsultant.firebase.api.TypeSort

data class Feature(
    var priceMin: Int? = null,
    var priceMax: Int? = null,
    var manufacturer: String? = null,
    var typeSort: TypeSort? = null,
    var map: MutableMap<String, Any> = mutableMapOf(),
    var nameCatalog: String? = null
)
