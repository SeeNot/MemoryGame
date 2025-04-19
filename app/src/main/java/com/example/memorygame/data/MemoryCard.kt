package com.example.memorygame.data

/**
 * Inspired from:
 * [developer.android.com](https://developer.android.com/codelabs/basic-android-kotlin-compose-navigation#3)
 */

data class MemoryCard(
    val id: Int = 0,
    val imageId: Int = 0,
    var isFaceUp: Boolean = false
)
