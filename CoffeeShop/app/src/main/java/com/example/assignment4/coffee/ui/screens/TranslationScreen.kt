package com.example.assignment4.coffee.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.assignment4.coffee.ui.components.AppTopBar

enum class TranslationTab { AUDIO, CAMERA, TEXT }

@Composable
fun TranslationScreen(
    onBack: () -> Unit,
    onSettings: () -> Unit
) {
    var selectedTab by remember { mutableStateOf(TranslationTab.AUDIO) }
    var audioTranslation by remember { mutableStateOf("") }
    var cameraTranslation by remember { mutableStateOf("") }
    var textInput by remember { mutableStateOf("") }
    var textTranslation by remember { mutableStateOf("") }
    var isRecording by remember { mutableStateOf(false) }
    var otherMeanings by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        AppTopBar(
            title = "Translate Menu",
            onBackClick = onBack,
            onSettingsClick = onSettings
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (selectedTab == TranslationTab.AUDIO) {
                Button(onClick = { selectedTab = TranslationTab.AUDIO }, modifier = Modifier.weight(1f)) { Text("Audio") }
            } else {
                OutlinedButton(onClick = { selectedTab = TranslationTab.AUDIO }, modifier = Modifier.weight(1f)) { Text("Audio") }
            }
            if (selectedTab == TranslationTab.CAMERA) {
                Button(onClick = { selectedTab = TranslationTab.CAMERA }, modifier = Modifier.weight(1f)) { Text("Camera") }
            } else {
                OutlinedButton(onClick = { selectedTab = TranslationTab.CAMERA }, modifier = Modifier.weight(1f)) { Text("Camera") }
            }
            if (selectedTab == TranslationTab.TEXT) {
                Button(onClick = { selectedTab = TranslationTab.TEXT }, modifier = Modifier.weight(1f)) { Text("Text") }
            } else {
                OutlinedButton(onClick = { selectedTab = TranslationTab.TEXT }, modifier = Modifier.weight(1f)) { Text("Text") }
            }
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            when (selectedTab) {
                TranslationTab.AUDIO -> AudioTranslationContent(
                    translation = audioTranslation,
                    otherMeanings = otherMeanings,
                    isRecording = isRecording,
                    onRecordClick = {
                        isRecording = !isRecording
                        if (!isRecording) {
                            audioTranslation = "Translated: [Your recorded speech would appear here]"
                            otherMeanings = "Other meanings: café (coffee shop), latte (milk coffee)"
                        }
                    }
                )
                TranslationTab.CAMERA -> CameraTranslationContent(
                    translation = cameraTranslation,
                    otherMeanings = otherMeanings,
                    onCaptureClick = {
                        cameraTranslation = "Translated: [Menu text from image would appear here]"
                        otherMeanings = "Other meanings: espresso, cappuccino, americano"
                    }
                )
                TranslationTab.TEXT -> TextTranslationContent(
                    inputText = textInput,
                    translation = textTranslation,
                    otherMeanings = otherMeanings,
                    onInputChange = { textInput = it },
                    onTranslateClick = {
                        textTranslation = if (textInput.isBlank()) "" else "Translated: $textInput"
                        otherMeanings = "Other meanings: (context clues for \"$textInput\")"
                    }
                )
            }
        }
    }
}

@Composable
private fun AudioTranslationContent(
    translation: String,
    otherMeanings: String,
    isRecording: Boolean,
    onRecordClick: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(
            text = "Tap to record speech. We'll translate what you hear.",
            style = MaterialTheme.typography.bodyLarge
        )
        Button(
            onClick = onRecordClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(if (isRecording) "Stop Recording" else "Hold to Record")
        }
        if (translation.isNotEmpty()) {
            Text(
                text = "Translation",
                style = MaterialTheme.typography.titleMedium
            )
            Text(text = translation, style = MaterialTheme.typography.bodyLarge)
            if (otherMeanings.isNotEmpty()) {
                Text(
                    text = "Other meanings",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Text(text = otherMeanings, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Composable
private fun CameraTranslationContent(
    translation: String,
    otherMeanings: String,
    onCaptureClick: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(
            text = "Point camera at menu or text.",
            style = MaterialTheme.typography.bodyLarge
        )
        Button(
            onClick = onCaptureClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text("Capture & Translate")
        }
        if (translation.isNotEmpty()) {
            Text(
                text = "Translation",
                style = MaterialTheme.typography.titleMedium
            )
            Text(text = translation, style = MaterialTheme.typography.bodyLarge)
            if (otherMeanings.isNotEmpty()) {
                Text(
                    text = "Other meanings",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Text(text = otherMeanings, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Composable
private fun TextTranslationContent(
    inputText: String,
    translation: String,
    otherMeanings: String,
    onInputChange: (String) -> Unit,
    onTranslateClick: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(
            text = "Type or paste text to translate.",
            style = MaterialTheme.typography.bodyLarge
        )
        OutlinedTextField(
            value = inputText,
            onValueChange = onInputChange,
            label = { Text("Text to translate") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
        )
        Button(
            onClick = onTranslateClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text("Translate")
        }
        if (translation.isNotEmpty()) {
            Text(
                text = "Translation",
                style = MaterialTheme.typography.titleMedium
            )
            Text(text = translation, style = MaterialTheme.typography.bodyLarge)
            if (otherMeanings.isNotEmpty()) {
                Text(
                    text = "Other meanings",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Text(text = otherMeanings, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
