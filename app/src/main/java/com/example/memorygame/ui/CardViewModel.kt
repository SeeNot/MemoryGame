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


    private var firstSelectedCard: MemoryCard? = null

    init {
        startNewGame()
    }

    private fun startNewGame() {

        // Duplicate the list and shuffle to produce pairs in random order.
        val cardIds = DataSource.imageIds
        val cardList = mutableListOf<MemoryCard>()
        for (i in cardIds.indices) {
            cardList.add(MemoryCard(id = i, imageId = cardIds[i]))
            cardList.add(MemoryCard(id = i + cardIds.size, imageId = cardIds[i]))
        }

        cardList.shuffle() // Randomize card positions

        _gameState.value = GameState(cards = cardList)

    }

    fun onCardClick(card: MemoryCard) {
        val currentState = _gameState.value

        if (card.isFaceUp) {
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
        _gameState.update { it.copy( moves = it.moves + 1) }
        val selected = _gameState.value.selectedCards

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
            }
        }
    }

    private fun hasGameEnded() {
        if (_gameState.value.cards.isNotEmpty()) {
            return
        }

    }



}