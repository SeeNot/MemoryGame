package com.example.memorygame.ui

import androidx.lifecycle.ViewModel
import com.example.memorygame.data.DataSource
import com.example.memorygame.data.GameState
import com.example.memorygame.data.MemoryCard
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

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


        _gameState.update {
            it.copy(cards = updatedCards)
        }

    }

}