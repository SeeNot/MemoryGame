package com.example.memorygame.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


enum class GameScreens{
    Home,
    Game
}

@Preview
@Composable
fun StartScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: GameViewModel = viewModel()
) {
    NavHost(
        navController = navController,
        startDestination = GameScreens.Home.name,
    ) {

        composable(route = GameScreens.Home.name) {
            SelectScreen(
                onNextButtonClicked = {
                    viewModel.setGameMode(it)
                    navController.navigate(GameScreens.Game.name)
                }
            )
        }
        composable(route = GameScreens.Game.name) {
            GameScreen(
                onNextButtonClicked = {},
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }
}

