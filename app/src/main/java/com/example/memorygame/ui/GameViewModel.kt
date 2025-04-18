package com.example.memorygame.ui

import androidx.lifecycle.ViewModel
import com.example.memorygame.data.GameData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GameViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(GameData())
    val uiState: StateFlow<GameData> = _uiState.asStateFlow()

    fun addP1Points() {
        _uiState.update { currentState ->
            currentState.copy(
                p1Points = _uiState.value.p1Points
            )
        }
    }

    fun addP2Points() {
        _uiState.update { currentState ->
            currentState.copy(
                p1Points = _uiState.value.p2Points
            )
        }
    }

    fun resetPoints() {
        _uiState.update { currentState ->
            currentState.copy(
                p1Points = 0,
                p2Points = 0
            )
        }
    }

    fun setGameMode(pVP: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                isPvp = pVP
            )
        }
    }

    fun changePlayerTurn() {
        _uiState.update { currentState ->
            currentState.copy(
                isPlayerOne = !_uiState.value.isPlayerOne
            )
        }
    }

}