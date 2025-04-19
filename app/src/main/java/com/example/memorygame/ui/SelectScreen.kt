package com.example.memorygame.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.memorygame.R
import com.example.memorygame.ui.components.GameButton
import androidx.compose.foundation.layout.Arrangement

@Composable
fun SelectScreen(
    onNextButtonClicked: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    )
    {
        Text(
            text = stringResource(R.string.who_playing),
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(10.dp),
            lineHeight = 40.sp
        )
        GameButton(
            message = stringResource(R.string.v_computer),
            onClick = {
                onNextButtonClicked(false)
            },
        )
        GameButton(
            message = stringResource(R.string.v_player_1),
            onClick = {
                onNextButtonClicked(true)
            },
        )
    }
}