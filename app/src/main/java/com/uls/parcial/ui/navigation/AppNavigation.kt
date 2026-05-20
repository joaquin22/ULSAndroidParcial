package com.uls.parcial.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.uls.parcial.ui.screens.GameScreen
import com.uls.parcial.ui.screens.ResultScreen
import com.uls.parcial.ui.screens.WelcomeScreen
import com.uls.parcial.viewmodel.GameViewModel

object Routes {
    const val WELCOME = "welcome"
    const val GAME    = "game"
    const val RESULT  = "result"
}

@Composable
fun AppNavigation(navController: NavHostController) {

    val viewModel: GameViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = Routes.WELCOME
    ) {

        composable(Routes.WELCOME) {
            WelcomeScreen(onStartGame = {
                viewModel.startGame()
                navController.navigate(Routes.GAME)
            })
        }

        composable(Routes.GAME) {
            if (uiState.isGameOver) {
                navController.navigate(Routes.RESULT) {
                    popUpTo(Routes.GAME) { inclusive = true }
                }
            }

            GameScreen(
                uiState = uiState,
                onColorSelected = viewModel::onColorSelected
            )
        }

        composable(Routes.RESULT) {
            ResultScreen(
                uiState = uiState,
                onPlayAgain = {
                    viewModel.startGame()
                    navController.navigate(Routes.GAME) {
                        popUpTo(Routes.RESULT) { inclusive = true }
                    }
                },
                onGoHome = {
                    navController.navigate(Routes.WELCOME) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}