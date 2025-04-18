package com.example.memorygame.data

data class GameState(
    val cards: List<MemoryCard> = emptyList(),
    val selectedCards: List<MemoryCard> = emptyList(),
    val moves: Int = 0,
    val isGameOver: Boolean = false,
)