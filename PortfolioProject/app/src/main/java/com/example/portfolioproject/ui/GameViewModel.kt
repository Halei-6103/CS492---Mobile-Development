/*
 * Ian Hale
 * OSU - halei@oregonstate.edu
 * CS 492
 */

package com.example.portfolioproject.ui

import android.app.Application
import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.portfolioproject.data.Clue
import com.example.portfolioproject.data.TreasureHuntRepository
import com.example.portfolioproject.util.Haversine
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/** Maximum distance in meters to consider the player "at" the clue location. */
private const val DISTANCE_THRESHOLD_METERS = 50.0

/**
 * UI state for the treasure hunt game.
 * Exposed via StateFlow for Compose recomposition.
 */
data class GameUiState(
    val clues: List<Clue> = emptyList(),
    val currentClueIndex: Int = 0,
    val hintRevealed: Boolean = false,
    val elapsedSeconds: Long = 0L,
    val isTimerRunning: Boolean = false,
    val isTimerPaused: Boolean = false,
    val currentLatitude: Double? = null,
    val currentLongitude: Double? = null,
    val showWrongLocationDialog: Boolean = false,
    val loadError: Boolean = false
) {
    val currentClue: Clue? = clues.getOrNull(currentClueIndex)
    val isFinalClue: Boolean = clues.isNotEmpty() && currentClueIndex == clues.lastIndex
    val hasNextClue: Boolean = currentClueIndex + 1 < clues.size
}

/**
 * ViewModel for the Treasure Hunter app.
 * Manages game state, timer, and location-based validation using MVVM.
 */
class GameViewModel(application: Application) : ViewModel() {

    private val repository = TreasureHuntRepository(application)

    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private var timerJob: Job? = null

    init {
        loadClues()
    }

    /** Loads clues from the raw JSON resource. */
    private fun loadClues() {
        val data = repository.loadClues()
        _uiState.update {
            if (data == null || data.clues.isEmpty()) {
                it.copy(loadError = true)
            } else {
                it.copy(clues = data.clues, loadError = false)
            }
        }
    }

    /** Updates the current device location (called from location updates). */
    fun updateLocation(latitude: Double?, longitude: Double?) {
        _uiState.update {
            it.copy(currentLatitude = latitude, currentLongitude = longitude)
        }
    }

    /** Updates location from Android Location object. */
    fun updateLocation(location: Location?) {
        if (location == null) {
            updateLocation(null, null)
        } else {
            updateLocation(location.latitude, location.longitude)
        }
    }

    /** Starts the game: starts the timer and moves to first clue. */
    fun startGame() {
        _uiState.update {
            it.copy(
                currentClueIndex = 0,
                hintRevealed = false,
                elapsedSeconds = 0L,
                isTimerRunning = true,
                isTimerPaused = false
            )
        }
        startTimer()
    }

    /** Starts the count-up timer. */
    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1_000L)
                _uiState.update { state ->
                    if (state.isTimerRunning && !state.isTimerPaused) {
                        state.copy(elapsedSeconds = state.elapsedSeconds + 1L)
                    } else state
                }
            }
        }
    }

    /** Pauses the timer (e.g. on Clue Solved screen). */
    fun pauseTimer() {
        _uiState.update { it.copy(isTimerPaused = true) }
    }

    /** Resumes the timer (e.g. when leaving Clue Solved for next clue). */
    fun resumeTimer() {
        _uiState.update { it.copy(isTimerPaused = false) }
    }

    /** Stops the timer permanently (e.g. on Completed screen). */
    fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
        _uiState.update {
            it.copy(isTimerRunning = false, isTimerPaused = false)
        }
    }

    /** Reveals the hint for the current clue. */
    fun revealHint() {
        _uiState.update { it.copy(hintRevealed = true) }
    }

    /**
     * Checks if the player is close enough to the current clue location using Haversine.
     * Returns true if within threshold, false otherwise.
     */
    fun checkLocationAndAdvance(): Boolean {
        val state = _uiState.value
        val clue = state.currentClue ?: return false
        val lat = state.currentLatitude
        val lon = state.currentLongitude
        if (lat == null || lon == null) {
            _uiState.update { it.copy(showWrongLocationDialog = true) }
            return false
        }
        val distanceMeters = Haversine.distanceMeters(
            lat, lon,
            clue.latitude, clue.longitude
        )
        if (distanceMeters > DISTANCE_THRESHOLD_METERS) {
            _uiState.update { it.copy(showWrongLocationDialog = true) }
            return false
        }
        // Correct location
        _uiState.update { it.copy(showWrongLocationDialog = false) }
        if (state.isFinalClue) {
            stopTimer()
        } else {
            pauseTimer()
        }
        return true
    }

    /** Moves to the next clue and resets hint; call after Clue Solved Continue. */
    fun goToNextClue() {
        _uiState.update {
            it.copy(
                currentClueIndex = it.currentClueIndex + 1,
                hintRevealed = false,
                showWrongLocationDialog = false
            )
        }
        resumeTimer()
    }

    /** Dismisses the "wrong location" dialog. */
    fun dismissWrongLocationDialog() {
        _uiState.update { it.copy(showWrongLocationDialog = false) }
    }

    /** Quit: reset hunt and return to start (timer is stopped). */
    fun quitAndReset() {
        timerJob?.cancel()
        timerJob = null
        _uiState.update {
            it.copy(
                currentClueIndex = 0,
                hintRevealed = false,
                elapsedSeconds = 0L,
                isTimerRunning = false,
                isTimerPaused = false,
                showWrongLocationDialog = false
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}
