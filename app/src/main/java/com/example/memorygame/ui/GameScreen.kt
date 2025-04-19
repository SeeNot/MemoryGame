package com.example.memorygame.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.memorygame.R
import com.example.memorygame.data.DataSource
import com.example.memorygame.data.MemoryCard


@Composable
fun GameScreen(
    onNextButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CardViewModel = viewModel()

) {
    val gameState by viewModel.gameState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        /** Make a list of playable cards that is randomized */
        val cardIdList = gameState.cards

        /** Will use iterator to keep track which image is already used (to not repeat) */
        val iterator = cardIdList.iterator()


        for (i in 0 until cardIdList.size / 2) {
            Row {
                GuessingCard(iterator.next(), onCardClick = { clickedCard ->
                    viewModel.onCardClick(clickedCard)
                })
                GuessingCard(iterator.next(), onCardClick = { clickedCard ->
                    viewModel.onCardClick(clickedCard)
                })
            }
        }

        if (gameState.isGameOver) {
            Text(
                /**
                 * TODO Nomainīt tekstu
                 */
                text = stringResource(R.string.who_playing),
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(10.dp),
                lineHeight = 40.sp
            )
        }
    }
}


@Composable
fun GuessingCard(memoryCard: MemoryCard,
                 onCardClick: (MemoryCard) -> Unit
) {
    Surface(
        color = Color.Gray,
        modifier = Modifier
            .size(130.dp)
            .padding(5.dp),
        onClick = {onCardClick(memoryCard)}
    ) {
        if (memoryCard.isFaceUp) {
            Image(
                painter = painterResource(id = memoryCard.imageId),
                contentDescription = null,
            )
        }
    }
}