package com.example.memorygame.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

/**
 * Inspired from:
 * [developer.android.com](https://developer.android.com/codelabs/basic-android-kotlin-compose-navigation#7)
 */
/**
 * The name of the two routes for the different screens
 */
enum class GameScreens{
    Home,
    Game
}

@Preview
@Composable
fun StartScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: CardViewModel = viewModel()
) {
    /**
     * Generated with Gemini 2.5 Pro:
     * What is the best way to implement a dialog box that asks for username and only saves it in the session ?
     * How to greet make a dialog entry show up after the user has entered information
     */
    var showUsernameDialog by remember { mutableStateOf(true) } // Show dialog initially
    var usernameInput by remember { mutableStateOf("") }
    var showGreetingDialog by remember { mutableStateOf(false) }


    when {
        showUsernameDialog -> {
            UsernameEntryDialog(
                currentInput = usernameInput,
                onUsernameChange = { usernameInput = it },
                onConfirm = {
                    if (usernameInput.isNotBlank()) {
                        viewModel.setUsername(usernameInput)
                        showUsernameDialog = false
                        showGreetingDialog = true
                    }
                }
            )
        }

        showGreetingDialog -> {
            SimpleGreetingAlertDialog(
                username = usernameInput,
                onDismissRequest = {
                    showGreetingDialog = false
                }
            )
        }

        else -> {
            /**
             * Two routes for main screen and for the game screen
             */
            NavHost(
                navController = navController,
                startDestination = GameScreens.Home.name,
            ) {
                composable(route = GameScreens.Home.name) {
                    SelectScreen(
                        onNextButtonClicked = { isPvpValue ->
                            viewModel.startNewGame()
                            viewModel.setGameMode(isPvpValue)
                            navController.navigate(GameScreens.Game.name)
                        }
                    )
                }
                composable(route = GameScreens.Game.name) {
                    GameScreen(
                        viewModel = viewModel,
                        onNextButtonClicked = {},
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}