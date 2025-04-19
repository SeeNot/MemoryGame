package com.example.memorygame.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

/**
 * Inspired from:
 * [developer.android.com](https://developer.android.com/codelabs/basic-android-kotlin-compose-navigation#3)
 */

@Composable
fun GameButton(message: String, onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
    ) {
        Text(
            message,
            fontSize = 20.sp
        )
    }
}