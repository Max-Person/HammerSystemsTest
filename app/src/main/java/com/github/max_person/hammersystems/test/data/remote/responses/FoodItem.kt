package com.github.max_person.hammersystems.test.data.remote.responses

data class FoodItem(
    val id: String,
    val img: String,
    val name: String,
    val dsc: String,
    val price: Float,
    val rate: Int,
    val country: String
)