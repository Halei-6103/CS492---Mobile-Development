/*
 * Ian Hale
 * OSU - halei@oregonstate.edu
 * CS 492
 */

package com.example.portfolioproject.data

import android.content.Context
import com.example.portfolioproject.R

/**
 * Repository that loads treasure hunt clue data from the raw JSON resource.
 * Ensures all clue data comes from a single source of truth (res/raw).
 */
class TreasureHuntRepository(private val context: Context) {

    /**
     * Loads and parses the treasure hunt clues from res/raw/treasure_hunt_clues.json.
     * Returns null if the resource is missing or invalid.
     */
    fun loadClues(): TreasureHuntData? {
        return try {
            val jsonString = context.resources.openRawResource(R.raw.treasure_hunt_clues)
                .bufferedReader()
                .use { it.readText() }
            TreasureHuntData.fromJson(jsonString)
        } catch (e: Exception) {
            null
        }
    }
}
