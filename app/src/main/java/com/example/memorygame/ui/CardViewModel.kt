package com.example.memorygame.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memorygame.data.DataSource
import com.example.memorygame.data.GameState
import com.example.memorygame.data.MemoryCard
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CardViewModel : ViewModel() {

    private val _gameState = MutableStateFlow(GameState())
    val gameState: StateFlow<GameState> = _gameState.asStateFlow()

    init {
        startNewGame()
    }

    fun startNewGame() {

        // Duplicate the list and shuffle to produce pairs in random order.
        val cardIds = DataSource.imageIds
        val cardList = mutableListOf<MemoryCard>()
        for (i in cardIds.indices) {
            cardList.add(MemoryCard(id = i, imageId = cardIds[i]))
            cardList.add(MemoryCard(id = i + cardIds.size, imageId = cardIds[i]))
        }

        cardList.shuffle() // Randomize card positions

        _gameState.value = GameState(cards = cardList)

        resetPoints()

    }

    fun onCardClick(card: MemoryCard) {
        val currentState = _gameState.value

        if (card.isFaceUp || currentState.disabledClickin) {
            return
        }

        val updatedCards = currentState.cards.map {
            if (card.id == it.id) it.copy(isFaceUp = true) else it
        }

        val clickedCard = updatedCards.find { it.id == card.id } ?: return // Should exist

        val currentSelected = currentState.selectedCards + clickedCard

        _gameState.update {
            it.copy(cards = updatedCards, selectedCards = currentSelected)
        }

        if (currentSelected.size >= 2) twoSelectedCards()

    }

    private fun twoSelectedCards() {
        _gameState.update { it.copy(moves = it.moves + 1) }
        val selected = _gameState.value.selectedCards

        /**
         * Used to suspend only the viewmodel and not the whole app, needs to be
         * before launching async task, otherwise it will only be in the given scope
         **/
        _gameState.update { it.copy(disabledClickin = true) }
        viewModelScope.launch {
            val card1 = selected[0]
            val card2 = selected[1]

            if (card1.imageId == card2.imageId) {
                delay(1000L)
                // Match found!
                val updatedCards = mutableListOf<MemoryCard>()
                for (card in _gameState.value.cards) {
                    if (card.imageId != card1.imageId) updatedCards.add(card)
                }

                _gameState.update {
                    it.copy(
                        cards = updatedCards,
                        selectedCards = emptyList(), // Clear selection
                    )
                }
                // Check for game over after state update
                hasGameEnded()

                // This player continuous to play
                if (_gameState.value.isPlayerOneTurn) {
                    addP1Points()
                } else {
                    addP2Points()
                }

            } else {
                delay(1000L) // Wait for 1 second
                val updatedCards = _gameState.value.cards.map {
                    if (it.id == card1.id || it.id == card2.id) {
                        it.copy(isFaceUp = false) // Flip back down
                    } else {
                        it
                    }
                }
                _gameState.update {
                    it.copy(
                        cards = updatedCards,
                        selectedCards = emptyList(), // Clear selection
                    )
                }
                changePlayerTurn()
            }
            /**
             * Allows users to click after the async task has ended otherwise the clicking
             * would be allowed before this async task would have ended
             **/
            _gameState.update { it.copy(disabledClickin = false) }
        }
    }

    private fun hasGameEnded() {
        if (_gameState.value.cards.isNotEmpty()) {
            return
        }

        _gameState.update { currentState ->
            currentState.copy(
                isGameOver = true
            )
        }
    }

    fun addP1Points() {
        _gameState.update { currentState ->
            currentState.copy(
                p1Points = _gameState.value.p1Points
            )
        }
    }

    fun addP2Points() {
        _gameState.update { currentState ->
            currentState.copy(
                p1Points = _gameState.value.p2Points
            )
        }
    }

    private fun resetPoints() {
        _gameState.update { currentState ->
            currentState.copy(
                p1Points = 0,
                p2Points = 0
            )
        }
    }

    fun setGameMode(pVP: Boolean) {
        _gameState.update { currentState ->
            currentState.copy(
                isPvp = pVP
            )
        }
    }

    fun changePlayerTurn() {
        _gameState.update { currentState ->
            currentState.copy(
                isPlayerOneTurn = !_gameState.value.isPlayerOneTurn
            )
        }
    }
}
