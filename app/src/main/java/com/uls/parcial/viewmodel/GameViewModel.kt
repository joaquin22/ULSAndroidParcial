package com.uls.parcial.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.uls.parcial.model.GameColor
import com.uls.parcial.model.GameResult
import com.uls.parcial.model.GameState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GameViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        const val POINTS_CORRECT = 10
        const val GAME_DURATION_SECONDS = 30
        const val POINTS_WRONG = -5
        const val PREFS_NAME = "color_game_prefs"
        const val KEY_HIGH_SCORE = "high_score"
        val GAME_COLORS = listOf(
            GameColor("Rojo",     0xFFE53935),
            GameColor("Azul",     0xFF1E88E5),
            GameColor("Verde",    0xFF43A047),
            GameColor("Amarillo", 0xFFFDD835),
            GameColor("Naranja",  0xFFFB8C00),
            GameColor("Morado",   0xFF8E24AA),
            GameColor("Rosa",     0xFFE91E8C),
            GameColor("Cian",     0xFF00ACC1),
        )
    }

    private val prefs = application.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private val _uiState = MutableStateFlow(
        GameState(highScore = prefs.getInt(KEY_HIGH_SCORE, 0))
    )
    val uiState: StateFlow<GameState> = _uiState.asStateFlow()

    private var timerJob: Job? = null
    private var gameCounter = 0

    fun startGame() {
        timerJob?.cancel()
        val currentHistory = _uiState.value.sessionHistory
        val currentHighScore = _uiState.value.highScore
        _uiState.update {
            GameState(
                isPlaying = true,
                score = 0,
                timeLeft = GAME_DURATION_SECONDS,
                correctAnswers = 0,
                wrongAnswers = 0,
                isGameOver = false,
                showFeedbackAnswer = false,
                lastAnswerCorrect = false,
                sessionHistory = currentHistory,
                highScore = currentHighScore
            )
        }
        nextRound()
        startTimer()
    }

    fun onColorSelected(color: GameColor) {
        val state = _uiState.value
        if (!state.isPlaying) return

        val isCorrect = color.name == state.targetColor?.name
        val newScore = (state.score + if (isCorrect) POINTS_CORRECT else POINTS_WRONG).coerceAtLeast(0)

        _uiState.update {
            it.copy(
                score = newScore,
                correctAnswers = if (isCorrect) it.correctAnswers + 1 else it.correctAnswers,
                wrongAnswers = if (!isCorrect) it.wrongAnswers + 1 else it.wrongAnswers,
                showFeedbackAnswer = true,
                lastAnswerCorrect = isCorrect
            )
        }

        viewModelScope.launch {
            delay(500)
            if (_uiState.value.isPlaying) {
                nextRound()
                _uiState.update { it.copy(showFeedbackAnswer = false) }
            }
        }
    }

    private fun nextRound() {
        val shuffled = GAME_COLORS.shuffled()
        val target = shuffled.first()
        val options = shuffled.take(4).shuffled()
        _uiState.update {
            it.copy(targetColor = target, colorOptions = options)
        }
    }

    private fun startTimer() {
        timerJob = viewModelScope.launch {
            while (_uiState.value.timeLeft > 0) {
                delay(1000)
                _uiState.update { it.copy(timeLeft = it.timeLeft - 1) }
            }
            endGame()
        }
    }

    private fun endGame() {
        timerJob?.cancel()
        gameCounter++
        val finalScore = _uiState.value.score
        val newHighScore = maxOf(_uiState.value.highScore, finalScore)

        if (finalScore > _uiState.value.highScore) {
            prefs.edit().putInt(KEY_HIGH_SCORE, finalScore).apply()
        }

        val result = GameResult(
            gameNumber = gameCounter,
            score = finalScore,
            correctAnswers = _uiState.value.correctAnswers,
            wrongAnswers = _uiState.value.wrongAnswers
        )

        _uiState.update {
            it.copy(
                isPlaying = false,
                isGameOver = true,
                showFeedbackAnswer = false,
                highScore = newHighScore,
                sessionHistory = listOf(result) + it.sessionHistory
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}
