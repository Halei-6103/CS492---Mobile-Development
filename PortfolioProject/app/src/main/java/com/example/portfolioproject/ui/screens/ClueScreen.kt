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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.portfolioproject.data.Clue
import com.example.portfolioproject.util.LocationHelper

/**
 * Formats elapsed seconds as MM:SS for the timer display.
 */
private fun formatElapsed(seconds: Long): String {
    val m = seconds / 60
    val s = seconds % 60
    return "%d:%02d".format(m, s)
}

/**
 * Clue page: current clue text, Hint button, count-up timer, Found It!, Quit.
 * Timer continues running here. Location updates are requested while this screen is visible.
 */
@Composable
fun ClueScreen(
    clue: Clue?,
    clueIndex: Int,
    totalClues: Int,
    hintRevealed: Boolean,
    elapsedSeconds: Long,
    showWrongLocationDialog: Boolean,
    onLocationUpdate: (Double, Double) -> Unit,
    onRevealHint: () -> Unit,
    onFoundIt: () -> Unit,
    onQuit: () -> Unit,
    onWrongLocationDismiss: () -> Unit
) {
    val context = LocalContext.current
    val locationHelper = remember { LocationHelper(context) }

    // Start location updates when on this screen (works with emulator Extended Controls → Location).
    LaunchedEffect(Unit) {
        locationHelper.startLocationUpdates { location ->
            onLocationUpdate(location.latitude, location.longitude)
        }
    }
    DisposableEffect(Unit) {
        onDispose { locationHelper.stopLocationUpdates() }
    }

    if (clue == null) {
        Text("No clue data", modifier = Modifier.padding(24.dp))
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        // Timer display (count-up, animated via state updates)
        Text(
            text = formatElapsed(elapsedSeconds),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Text(
            text = "Clue ${clueIndex + 1} of $totalClues",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = clue.clueText,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (hintRevealed) {
            Text(
                text = "Hint: ${clue.hintText}",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
        } else {
            TextButton(onClick = onRevealHint) {
                Text("Show hint")
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onFoundIt,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Found It!")
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextButton(
            onClick = onQuit,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Quit")
        }
    }

    if (showWrongLocationDialog) {
        AlertDialog(
            onDismissRequest = onWrongLocationDismiss,
            title = { Text("Not quite right") },
            text = { Text("You're not close enough to this location. Keep looking! The timer is still running.") },
            confirmButton = {
                TextButton(onClick = onWrongLocationDismiss) {
                    Text("OK")
                }
            }
        )
    }
}
