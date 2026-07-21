/*
 * Ian Hale
 * OSU - halei@oregonstate.edu
 * CS 492
 */

package com.example.portfolioproject.data

/**
 * Data class representing a single clue in the treasure hunt.
 * Loaded from the JSON resource file.
 */
data class Clue(
    val clueText: String,
    val hintText: String,
    val latitude: Double,
    val longitude: Double,
    val locationInfo: String
)
