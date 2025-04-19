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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.ui.window.DialogProperties

/**
 * Inspired from: 
 * [developer.android.com](https://developer.android.com/codelabs/basic-android-kotlin-compose-navigation#3)
 */

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

/**
 * Generated with Gemini 2.5 Pro:
 * What is the best way to implement a dialog box that asks for
 * username and only saves it in the session
 */
@Composable
fun UsernameEntryDialog(
    currentInput: String,
    onUsernameChange: (String) -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { }, // Leave empty to ignore internal dismiss requests

        // Use DialogProperties to control external dismissal behaviour
        properties = DialogProperties(
            dismissOnClickOutside = false, // Prevent closing by clicking outside
            dismissOnBackPress = false     // Prevent closing with the back button/gesture
        ),

        title = { Text("Enter Your Name") },
        text = {
            OutlinedTextField(
                value = currentInput,
                onValueChange = onUsernameChange,
                label = { Text("Username") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                enabled = currentInput.isNotBlank()
            ) {
                Text("Confirm")
            }
        }
    )
}

@Composable
fun SimpleGreetingAlertDialog(
    username: String,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(text = "Hi $username!") // Greeting in the title slot
        },
        confirmButton = {
            TextButton(
                onClick = onDismissRequest // Dismiss when OK is clicked
            ) {
                Text("OK")
            }
        }
    )
}