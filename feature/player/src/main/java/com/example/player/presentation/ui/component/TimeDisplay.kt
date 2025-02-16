package com.example.player.presentation.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TimeDisplay(currentPosition: Long, duration: Long) {
    Row {
        Text(text = formatTime(currentPosition))
        Spacer(modifier = Modifier.weight(1f))
        Text(text = formatTime(duration))
    }
}

fun formatTime(millis: Long): String {
    val seconds = millis / 1000
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return String.format("%02d:%02d", minutes, remainingSeconds)
}