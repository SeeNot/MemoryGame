package com.example.memorygame.data

data class GameState(
    val cards: List<MemoryCard> = emptyList(),
    val selectedCards: List<MemoryCard> = emptyList(),
    val moves: Int = 0,
    val disabledClickin: Boolean = false,
    val isGameOver: Boolean = false,
    /** Who is playing. "0" for player 1 and 1 for computer or player 2 */
    val isPlayerOneTurn: Boolean = true,
    /** Which mode is selected 0 for pvp, 1 for pvc */
    val isPvp: Boolean = true,
    val p1Points: Int = 0,
    val p2Points: Int = 0,
    val winnerIsP1: Boolean = true
)