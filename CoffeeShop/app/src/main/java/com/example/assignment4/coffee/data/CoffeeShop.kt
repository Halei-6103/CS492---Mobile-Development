package com.example.assignment4.coffee.data

/**
 * Represents a coffee shop in Corvallis, Oregon.
 */
data class CoffeeShop(
    val id: String,
    val name: String,
    val address: String,
    val knownFor: String,
    val popularDrinks: String,
    val atmosphere: String
)
