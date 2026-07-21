/*
 * Ian Hale
 * OSU - halei@oregonstate.edu
 * CS 492
 */

package com.example.portfolioproject.ui.navigation

/**
 * Sealed class representing all navigation destinations in the app.
 * Used with Compose Navigation for type-safe routing.
 */
sealed class NavRoute(val route: String) {
    /** First screen: request location permission. */
    data object Permissions : NavRoute("permissions")

    /** Start screen: title, rules, Start Game button. */
    data object Start : NavRoute("start")

    /** Clue screen: current clue, hint, timer, Found It!, Quit. */
    data object Clue : NavRoute("clue")

    /** Shown after solving a non-final clue; shows elapsed time and location info. */
    data object ClueSolved : NavRoute("clue_solved")

    /** Shown after solving the final clue; treasure hunt completed. */
    data object Completed : NavRoute("completed")
}
