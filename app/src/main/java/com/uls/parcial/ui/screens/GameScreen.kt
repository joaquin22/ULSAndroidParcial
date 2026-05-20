package com.uls.parcial.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uls.parcial.model.GameColor
import com.uls.parcial.model.GameState
import com.uls.parcial.viewmodel.GameViewModel

@Composable
fun GameScreen(
    uiState: GameState,
    onColorSelected: (GameColor) -> Unit
){
    val timeProgress = uiState.timeLeft / GameViewModel.GAME_DURATION_SECONDS.toFloat()
    val timerColor by animateColorAsState(
        targetValue = when {
            timeProgress > 0.5f -> Color(0xFF64FFDA)
            timeProgress > 0.25f -> Color(0xFFFDD835)
            else -> Color(0xFFFF5252)
        },
        label = "timer"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(listOf(Color(0xFF0D0D1A), Color(0xFF1A1A2E)))
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFF16213E))
                    .padding(horizontal = 20.dp, vertical = 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.Start) {
                    Text("PUNTOS", color = Color(0xFF8892B0), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    Text(
                        text = "${uiState.score}",
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }

                Box(contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(
                        progress = { timeProgress },
                        modifier = Modifier.size(64.dp),
                        color = timerColor,
                        trackColor = Color(0xFF2A2A3E),
                        strokeWidth = 5.dp
                    )
                    Text(
                        text = "${uiState.timeLeft}",
                        color = timerColor,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("✅ ", fontSize = 12.sp)
                        Text("${uiState.correctAnswers}", color = Color(0xFF64FFDA), fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("❌ ", fontSize = 12.sp)
                        Text("${uiState.wrongAnswers}", color = Color(0xFFFF5252), fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Toca el botón del color mostrado",
                color = Color(0xFF8892B0),
                fontSize = 13.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(20.dp))

            uiState.targetColor?.let { target ->
                val displayColor = Color(target.colorValue)
                val borderColor by animateColorAsState(
                    targetValue = if (uiState.showFeedbackAnswer) {
                        if (uiState.lastAnswerCorrect) Color(0xFF64FFDA) else Color(0xFFFF5252)
                    } else Color.Transparent,
                    label = "border"
                )

                Box(
                    modifier = Modifier
                        .size(180.dp)
                        .clip(CircleShape)
                        .background(displayColor)
                        .border(4.dp, borderColor, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    if (uiState.showFeedbackAnswer) {
                        Text(
                            text = if (uiState.lastAnswerCorrect) "✓" else "✗",
                            color = Color.White,
                            fontSize = 56.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    uiState.colorOptions.chunked(2).forEach { row ->
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            row.forEach { color ->
                                ColorButton(
                                    color = color,
                                    onClick = { onColorSelected(color) },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                            if (row.size == 1) Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun ColorButton(
    color: GameColor,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val btnColor = Color(color.colorValue)

    Button(
        onClick = onClick,
        modifier = modifier.height(62.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = btnColor,
            contentColor = contentColorFor(btnColor),
            disabledContainerColor = btnColor.copy(alpha = 0.5f),
            disabledContentColor = Color.White.copy(alpha = 0.5f)
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
    ) {
        Text(
            text = color.name,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

private fun contentColorFor(background: Color): Color {
    val luminance = 0.299f * background.red + 0.587f * background.green + 0.114f * background.blue
    return if (luminance > 0.55f) Color(0xFF0D0D1A) else Color.White
}
