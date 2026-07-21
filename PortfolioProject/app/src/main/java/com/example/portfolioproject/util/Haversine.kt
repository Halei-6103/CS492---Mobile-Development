/*
 * Ian Hale
 * OSU - halei@oregonstate.edu
 * CS 492
 */

package com.example.portfolioproject.util

import kotlin.math.*

/**
 * Haversine formula implementation for calculating the great-circle distance
 * between two points on Earth given their latitude and longitude in decimal degrees.
 * Returns distance in meters.
 *
 * Formula: a = sin²(Δlat/2) + cos(lat1) * cos(lat2) * sin²(Δlon/2)
 *          c = 2 * atan2(√a, √(1−a))
 *          d = R * c  (R = Earth's radius in meters)
 *
 * This is used to check if the player is physically close enough to the clue location.
 */
object Haversine {

    /** Earth's radius in meters (mean radius). */
    private const val EARTH_RADIUS_METERS = 6_371_000.0

    /**
     * Calculates the distance in meters between two geographic points.
     *
     * @param lat1 Latitude of first point (decimal degrees).
     * @param lon1 Longitude of first point (decimal degrees).
     * @param lat2 Latitude of second point (decimal degrees).
     * @param lon2 Longitude of second point (decimal degrees).
     * @return Distance in meters.
     */
    fun distanceMeters(
        lat1: Double,
        lon1: Double,
        lat2: Double,
        lon2: Double
    ): Double {
        val lat1Rad = lat1 * PI / 180
        val lat2Rad = lat2 * PI / 180
        val deltaLat = (lat2 - lat1) * PI / 180
        val deltaLon = (lon2 - lon1) * PI / 180

        val a = sin(deltaLat / 2).pow(2) +
                cos(lat1Rad) * cos(lat2Rad) * sin(deltaLon / 2).pow(2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        return EARTH_RADIUS_METERS * c
    }
}
