/*
 * Ian Hale
 * OSU - halei@oregonstate.edu
 * CS 492
 */
package com.example.myapplication.model

import java.util.UUID

/**
 * Model layer: holds categories and recommendations in memory.
 * Enforces unique category names and unique recommendation place names per category.
 * Pre-populated with Corvallis categories and real place recommendations.
 */
class CityCategoriesRepository {

    private val _categories = mutableListOf<Category>()
    private val _recommendations = mutableListOf<Recommendation>()

    init {
        val foodId = UUID.randomUUID().toString()
        val drinksId = UUID.randomUUID().toString()
        val entertainmentId = UUID.randomUUID().toString()
        _categories.add(Category(id = foodId, name = "Food"))
        _categories.add(Category(id = drinksId, name = "Drinks"))
        _categories.add(Category(id = entertainmentId, name = "Entertainment"))

        listOf(
            Triple(foodId, "Local Boyz", "Hawaiian plate lunch spot, great for a quick and filling meal."),
            Triple(foodId, "Block 15 Brewery", "Brewpub with solid burgers and house beers."),
            Triple(foodId, "Tarn Tip Thai", "Reliable Thai food near campus."),
            Triple(drinksId, "Interzone", "Coffee and tea, good for studying."),
            Triple(drinksId, "Peacock Bar and Grill", "Historic downtown bar since 1929; full bar, pool tables, karaoke, and breakfast through dinner daily."),
            Triple(drinksId, "Crow Bar", "Craft cocktails and thick-crust pizza by the slice; cozy cocktail bar with rooftop and daily specials."),
            Triple(entertainmentId, "Darkside Cinema", "Independent movie theater downtown."),
            Triple(entertainmentId, "Benton County Fairgrounds", "Seasonal events and county fair."),
            Triple(entertainmentId, "OSU campus", "Walking the quad and visiting the library.")
        ).forEach { (catId, name, detail) ->
            _recommendations.add(
                Recommendation(
                    id = UUID.randomUUID().toString(),
                    placeName = name,
                    detailText = detail,
                    categoryId = catId
                )
            )
        }
    }

    fun getCategories(): List<Category> = _categories.toList()

    fun getCategory(id: String): Category? = _categories.find { it.id == id }

    fun getRecommendations(categoryId: String): List<Recommendation> =
        _recommendations.filter { it.categoryId == categoryId }

    fun getRecommendation(id: String): Recommendation? = _recommendations.find { it.id == id }

    /**
     * Adds a recommendation. Returns true if added, false if place name already exists in this category.
     */
    fun addRecommendation(categoryId: String, placeName: String): Boolean {
        val trimmed = placeName.trim()
        if (trimmed.isBlank()) return false
        if (_recommendations.any { it.categoryId == categoryId && it.placeName.equals(trimmed, ignoreCase = true) })
            return false
        _recommendations.add(
            Recommendation(
                id = UUID.randomUUID().toString(),
                placeName = trimmed,
                detailText = null,
                categoryId = categoryId
            )
        )
        return true
    }

    fun deleteRecommendation(id: String): Boolean = _recommendations.removeIf { it.id == id }

    fun updateRecommendationDetails(id: String, detailText: String): Boolean {
        val idx = _recommendations.indexOfFirst { it.id == id }
        if (idx < 0) return false
        val r = _recommendations[idx]
        _recommendations[idx] = r.copy(detailText = detailText.trim().ifBlank { null })
        return true
    }
}
