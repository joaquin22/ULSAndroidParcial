package com.uls.parcial.model

data class GameColor(
    val name: String,
    val colorValue: Long
)

data class GameResult(
    val gameNumber: Int,
    val score: Int,
    val correctAnswers: Int,
    val wrongAnswers: Int,
)

data class GameState(
    val isPlaying: Boolean = false,
    val isGameOver: Boolean = false,
    val score: Int = 0,
    val timeLeft: Int = 30,
    val targetColor: GameColor? = null,
    val colorOptions: List<GameColor> = emptyList(),
    val correctAnswers: Int = 0,
    val wrongAnswers: Int = 0,
    val showFeedbackAnswer: Boolean = false,
    val lastAnswerCorrect: Boolean = false,
    val sessionHistory: List<GameResult> = emptyList(),
    val highScore: Int = 0
)
