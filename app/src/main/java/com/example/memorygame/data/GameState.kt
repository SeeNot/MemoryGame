package com.example.memorygame.data

/**
 * Inspired from:
 * [developer.android.com](https://developer.android.com/codelabs/basic-android-kotlin-compose-navigation#7)
 */

/**
 * The main game state
 */

data class GameState(
    /** all of the cards */
    val cards: List<MemoryCard> = emptyList(),
    /** Currently selected cards */
    val selectedCards: List<MemoryCard> = emptyList(),
    val disabledClickin: Boolean = false,
    val isGameOver: Boolean = false,
    /** Who is playing. "0" for player 1 and 1 for computer or player 2 */
    val isPlayerOneTurn: Boolean = true,
    /** Which mode is selected 0 for pvp, 1 for pvc */
    val isPvp: Boolean = true,
    val p1Points: Int = 0,
    val p2Points: Int = 0,
    val username: String = "Player"
)