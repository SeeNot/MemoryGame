package com.example.memorygame.data

data class GameData(
    /** Who is playing. "0" for player 1 and 1 for computer or player 2 */
    val isPlayerOne: Boolean = true,
    /** Which mode is selected 0 for pvp, 1 for pvc */
    val isPvp: Boolean = true,
    val p1Points: Int = 0,
    val p2Points: Int = 0
)
