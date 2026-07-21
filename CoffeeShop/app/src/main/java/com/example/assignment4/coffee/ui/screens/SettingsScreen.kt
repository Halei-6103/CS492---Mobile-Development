package com.example.assignment4.coffee.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.assignment4.coffee.ui.components.AppTopBar
import com.example.assignment4.coffee.ui.settings.FontSizeOption

@Composable
fun SettingsScreen(
    currentFontSize: FontSizeOption,
    onFontSizeSelected: (FontSizeOption) -> Unit,
    onBack: () -> Unit,
    onSettings: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        AppTopBar(
            title = "Settings",
            onBackClick = onBack,
            onSettingsClick = onSettings
        )
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Text size",
                style = MaterialTheme.typography.titleLarge
            )
            FontSizeOption.entries.forEach { option ->
                Button(
                    onClick = { onFontSizeSelected(option) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = currentFontSize != option
                ) {
                    Text(text = option.label)
                }
            }
        }
    }
}
