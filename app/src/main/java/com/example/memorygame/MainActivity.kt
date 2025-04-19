package com.example.memorygame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.memorygame.ui.StartScreen
import com.example.memorygame.ui.theme.MemoryGameTheme

/**
 * Inspired from
 * @see developer.android.com/codelabs/basic-android-kotlin-compose-navigation#3
 */

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MemoryGameTheme {
                StartScreen()
            }
        }
    }
}