package com.example.player.presentation.ui.component

import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun ProgressSlider(currentPosition: Long, duration: Long, onValueChange: (Float) -> Unit) {
    Slider(
        value = if (duration > 0) (currentPosition.toFloat() / duration) else 0f,
        onValueChange = onValueChange,
        valueRange = 0f..1f,
        colors = SliderDefaults.colors(
            thumbColor = Color.Black,
            activeTrackColor = Color.DarkGray,
            inactiveTrackColor = Color.Gray,
        )
    )
}