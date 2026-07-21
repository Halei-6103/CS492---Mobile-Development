/*
 * Ian Hale
 * OSU - halei@oregonstate.edu
 * CS 492
 */
package com.example.myapplication.model

/**
 * Data class representing a recommended place within a category.
 * detailText is null until the user adds details on the detail screen.
 */
data class Recommendation(
    val id: String,
    val placeName: String,
    val detailText: String? = null,
    val categoryId: String
)
