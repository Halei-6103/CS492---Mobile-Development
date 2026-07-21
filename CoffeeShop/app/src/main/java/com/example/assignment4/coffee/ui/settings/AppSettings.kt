package com.example.assignment4.coffee.ui.settings

import androidx.compose.runtime.compositionLocalOf

/**
 * Font scale for accessibility. 1f = default, 1.2f = large, 1.4f = larger, 1.6f = largest.
 */
data class FontScale(val scale: Float)

val DefaultFontScale = FontScale(1f)
val LocalFontScale = compositionLocalOf { DefaultFontScale }

enum class FontSizeOption(val label: String, val scale: Float) {
    SMALL("Small", 0.9f),
    MEDIUM("Medium", 1f),
    LARGE("Large", 1.2f),
    LARGEST("Largest", 1.4f)
}
