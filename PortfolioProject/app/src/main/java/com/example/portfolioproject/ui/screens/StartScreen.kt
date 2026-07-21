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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

private val gameRules = """
    • Your goal is to find each hidden location by following clues.
    • Only one clue is shown at a time. Read it and go to the place it describes.
    • When you think you're there, tap "Found It!". The app will check your GPS location.
    • If you're close enough to the correct spot, you'll see the Clue Solved screen and can continue to the next clue.
    • If you're not close enough, a message will tell you to try again. The timer keeps running.
    • Use the Hint button if you're stuck.
    • You can Quit anytime to return to this screen and start over.
    • To test in the emulator: Extended Controls (…) → Location → set a custom latitude/longitude that matches a clue, then tap Found It!
""".trimIndent()

/**
 * Start page: app title, scrollable game rules, Start Game button.
 * Start begins the timer and navigates to the first clue.
 */
@Composable
fun StartScreen(
    cluesLoaded: Boolean,
    onStartGame: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = "Treasure Hunter",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            textAlign = TextAlign.Center
        )
        Text(
            text = "Game rules",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
        ) {
            Text(
                text = gameRules,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (!cluesLoaded) {
            Text(
                text = "Could not load clues. Please check the app data.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        Button(
            onClick = onStartGame,
            modifier = Modifier.fillMaxWidth(),
            enabled = cluesLoaded
        ) {
            Text("Start Game")
        }
    }
}
