package com.example.haconsultant.model

import java.io.Serializable

data class Orders(
    val name: String,
    val status: Int,
    val data: String
) : Serializable
