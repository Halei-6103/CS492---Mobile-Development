/*
 * Ian Hale
 * OSU - halei@oregonstate.edu
 * CS 492
 */

package com.example.portfolioproject.data

import org.json.JSONObject

/**
 * Wrapper for the full treasure hunt data loaded from JSON.
 * Used when parsing res/raw/treasure_hunt_clues.json.
 */
data class TreasureHuntData(
    val clues: List<Clue>
) {
    companion object {
        /**
         * Parses the raw JSON string from the resource file into TreasureHuntData.
         */
        fun fromJson(jsonString: String): TreasureHuntData {
            val json = JSONObject(jsonString)
            val cluesArray = json.getJSONArray("clues")
            val clues = mutableListOf<Clue>()
            for (i in 0 until cluesArray.length()) {
                val obj = cluesArray.getJSONObject(i)
                clues.add(
                    Clue(
                        clueText = obj.getString("clueText"),
                        hintText = obj.getString("hintText"),
                        latitude = obj.getDouble("latitude"),
                        longitude = obj.getDouble("longitude"),
                        locationInfo = obj.getString("locationInfo")
                    )
                )
            }
            return TreasureHuntData(clues = clues)
        }
    }
}
