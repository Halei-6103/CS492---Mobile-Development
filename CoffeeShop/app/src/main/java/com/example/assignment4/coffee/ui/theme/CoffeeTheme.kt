package com.example.assignment4.coffee.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

/**
 * Returns a typography with all font sizes scaled by [scale].
 */
fun Typography.scaledBy(scale: Float): Typography = Typography(
    displayLarge = displayLarge.scale(scale),
    displayMedium = displayMedium.scale(scale),
    displaySmall = displaySmall.scale(scale),
    headlineLarge = headlineLarge.scale(scale),
    headlineMedium = headlineMedium.scale(scale),
    headlineSmall = headlineSmall.scale(scale),
    titleLarge = titleLarge.scale(scale),
    titleMedium = titleMedium.scale(scale),
    titleSmall = titleSmall.scale(scale),
    bodyLarge = bodyLarge.scale(scale),
    bodyMedium = bodyMedium.scale(scale),
    bodySmall = bodySmall.scale(scale),
    labelLarge = labelLarge.scale(scale),
    labelMedium = labelMedium.scale(scale),
    labelSmall = labelSmall.scale(scale)
)

private fun TextStyle.scale(scale: Float): TextStyle = copy(
    fontSize = fontSize.times(scale),
    lineHeight = lineHeight.times(scale),
    letterSpacing = letterSpacing.times(scale)
)
