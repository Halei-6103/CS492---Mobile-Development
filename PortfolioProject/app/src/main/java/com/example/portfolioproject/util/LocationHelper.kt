/*
 * Ian Hale
 * OSU - halei@oregonstate.edu
 * CS 492
 */

package com.example.portfolioproject.util

import android.content.Context
import android.location.Location
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

/**
 * Helper for requesting location updates using FusedLocationProviderClient.
 * Lifecycle-aware: call startLocationUpdates() when on the Clue screen and
 * stopLocationUpdates() when leaving (e.g. in DisposableEffect).
 */
class LocationHelper(context: Context) {

    private val fusedClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context.applicationContext)

    private var locationCallback: LocationCallback? = null

    /**
     * Starts receiving location updates and invokes [onLocationUpdate] for each update.
     * Use for testing with emulator Extended Controls > Location.
     */
    /** Continuous updates so "Found It!" checks use a recent position (works with emulator mock location). */
    fun startLocationUpdates(onLocationUpdate: (Location) -> Unit) {
        val continuousRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5_000L).apply {
            setMinUpdateIntervalMillis(3_000L)
            setWaitForAccurateLocation(false)
        }.build()

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                result.lastLocation?.let { onLocationUpdate(it) }
            }
        }
        fusedClient.requestLocationUpdates(
            continuousRequest,
            locationCallback!!,
            Looper.getMainLooper()
        )
    }

    /** Stops location updates. Call when leaving the Clue screen. */
    fun stopLocationUpdates() {
        locationCallback?.let { fusedClient.removeLocationUpdates(it) }
        locationCallback = null
    }
}
