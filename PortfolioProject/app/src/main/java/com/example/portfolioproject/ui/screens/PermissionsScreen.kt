/*
 * Ian Hale
 * OSU - halei@oregonstate.edu
 * CS 492
 */

package com.example.portfolioproject.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 * First screen: explains why location is needed and requests ACCESS_FINE_LOCATION.
 * Handles granted and denied states clearly.
 */
@Composable
fun PermissionsScreen(
    hasPermission: Boolean,
    onRequestPermission: () -> Unit,
    onPermissionGranted: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Location Permission",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = if (hasPermission) {
                "Location access is allowed. Tap Continue to start the treasure hunt."
            } else {
                "Treasure Hunter needs your location to check when you reach each clue. " +
                        "We only use it to verify you are physically at the correct spot—we don't store or share your location."
            },
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = if (hasPermission) onPermissionGranted else onRequestPermission,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (hasPermission) "Continue" else "Allow location access")
        }
    }
}
