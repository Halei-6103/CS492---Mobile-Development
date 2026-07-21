/*
 * Ian Hale
 * OSU - halei@oregonstate.edu
 * CS 492
 */

package com.example.portfolioproject.ui.navigation

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.portfolioproject.ui.GameViewModel
import com.example.portfolioproject.ui.screens.ClueScreen
import com.example.portfolioproject.ui.screens.ClueSolvedScreen
import com.example.portfolioproject.ui.screens.CompletedScreen
import com.example.portfolioproject.ui.screens.PermissionsScreen
import com.example.portfolioproject.ui.screens.StartScreen

/**
 * Main navigation graph. First screen is Permissions, then Start, Clue, ClueSolved, or Completed.
 */
@Composable
fun AppNavigation(
    viewModel: GameViewModel,
    navController: NavHostController = rememberNavController()
) {
    val context = LocalContext.current
    var hasLocationPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED
        )
    }
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        hasLocationPermission = granted
    }

    NavHost(
        navController = navController,
        startDestination = NavRoute.Permissions.route
    ) {
        composable(NavRoute.Permissions.route) {
            PermissionsScreen(
                hasPermission = hasLocationPermission,
                onRequestPermission = {
                    permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                },
                onPermissionGranted = {
                    navController.navigate(NavRoute.Start.route) {
                        popUpTo(NavRoute.Permissions.route) { inclusive = true }
                    }
                }
            )
        }

        composable(NavRoute.Start.route) {
            val startState by viewModel.uiState.collectAsState()
            StartScreen(
                cluesLoaded = !startState.loadError && startState.clues.isNotEmpty(),
                onStartGame = {
                    viewModel.startGame()
                    navController.navigate(NavRoute.Clue.route) {
                        popUpTo(NavRoute.Start.route) { inclusive = true }
                    }
                }
            )
        }

        composable(NavRoute.Clue.route) {
            val state by viewModel.uiState.collectAsState()
            ClueScreen(
                clue = state.currentClue,
                clueIndex = state.currentClueIndex,
                totalClues = state.clues.size,
                hintRevealed = state.hintRevealed,
                elapsedSeconds = state.elapsedSeconds,
                showWrongLocationDialog = state.showWrongLocationDialog,
                onLocationUpdate = { lat, lon -> viewModel.updateLocation(lat, lon) },
                onRevealHint = viewModel::revealHint,
                onFoundIt = {
                    val correct = viewModel.checkLocationAndAdvance()
                    if (correct) {
                        val isFinal = viewModel.uiState.value.isFinalClue
                        if (isFinal) {
                            navController.navigate(NavRoute.Completed.route) {
                                popUpTo(NavRoute.Clue.route) { inclusive = true }
                            }
                        } else {
                            navController.navigate(NavRoute.ClueSolved.route)
                        }
                    }
                },
                onQuit = {
                    viewModel.quitAndReset()
                    navController.navigate(NavRoute.Start.route) {
                        popUpTo(NavRoute.Clue.route) { inclusive = true }
                    }
                },
                onWrongLocationDismiss = viewModel::dismissWrongLocationDialog
            )
        }

        composable(NavRoute.ClueSolved.route) {
            val state by viewModel.uiState.collectAsState()
            val locationInfo = state.currentClue?.locationInfo ?: ""
            ClueSolvedScreen(
                elapsedSeconds = state.elapsedSeconds,
                locationInfo = locationInfo,
                onContinue = {
                    viewModel.goToNextClue()
                    navController.popBackStack()
                    // We're now back on Clue with the next clue index
                }
            )
        }

        composable(NavRoute.Completed.route) {
            val state by viewModel.uiState.collectAsState()
            val locationInfo = state.currentClue?.locationInfo ?: ""
            CompletedScreen(
                finalTimeSeconds = state.elapsedSeconds,
                finalLocationInfo = locationInfo,
                onHome = {
                    viewModel.quitAndReset()
                    navController.navigate(NavRoute.Start.route) {
                        popUpTo(NavRoute.Completed.route) { inclusive = true }
                    }
                }
            )
        }
    }
}
