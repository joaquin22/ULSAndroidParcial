package com.uls.parcial.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uls.parcial.model.GameResult
import com.uls.parcial.model.GameState
import com.uls.parcial.ui.components.StatCard

@Composable
fun ResultScreen(
    uiState: GameState,
    onPlayAgain: () -> Unit,
    onGoHome: () -> Unit
) {
    val total = uiState.correctAnswers + uiState.wrongAnswers
    val accuracy = if (total > 0) (uiState.correctAnswers * 100f / total).toInt() else 0
    val isNewRecord = uiState.score == uiState.highScore && uiState.score > 0

    val medal = when {
        accuracy >= 90 -> "🥇"
        accuracy >= 70 -> "🥈"
        accuracy >= 50 -> "🥉"
        else -> "🎮"
    }
    val rankLabel = when {
        accuracy >= 90 -> "¡MAESTRO DEL COLOR!"
        accuracy >= 70 -> "¡Muy bien hecho!"
        accuracy >= 50 -> "¡Buen intento!"
        else -> "¡Sigue practicando!"
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFF0D0D1A), Color(0xFF1A1A2E))))
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            contentPadding = PaddingValues(vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(28.dp))
                        .background(Color(0xFF16213E).copy(alpha = 0.97f))
                        .padding(28.dp)
                ) {
                    Text(text = medal, fontSize = 56.sp)
                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = rankLabel,
                        color = Color(0xFF64FFDA),
                        fontSize = 17.sp,
                        fontWeight = FontWeight.ExtraBold,
                        textAlign = TextAlign.Center,
                        letterSpacing = 1.sp
                    )

                    if (isNewRecord) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = Color(0xFFFDD835).copy(0.15f)
                        ) {
                            Text(
                                text = "🏆  ¡Nuevo Récord!",
                                color = Color(0xFFFDD835),
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "${uiState.score}",
                        color = Color.White,
                        fontSize = 68.sp,
                        fontWeight = FontWeight.Black,
                    )
                    Text(
                        text = "PUNTOS",
                        color = Color(0xFF8892B0),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 3.sp
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        StatCard("✅ Correctas", "${uiState.correctAnswers}", Color(0xFF64FFDA), Modifier.weight(1f))
                        StatCard("❌ Errores",   "${uiState.wrongAnswers}",   Color(0xFFFF5252),  Modifier.weight(1f))
                        StatCard("🎯 Precisión", "$accuracy%",               Color(0xFFFDD835),  Modifier.weight(1f))
                    }
                }
            }

            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFF0F3460).copy(0.7f))
                        .padding(horizontal = 20.dp, vertical = 14.dp)
                ) {
                    Text("🏆", fontSize = 22.sp)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "RÉCORD HISTÓRICO",
                            color = Color(0xFF8892B0),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 2.sp
                        )
                        Text(
                            text = "${uiState.highScore} puntos",
                            color = Color(0xFFFDD835),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = if (isNewRecord) "NUEVO ✨" else "",
                        color = Color(0xFFFDD835),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            if (uiState.sessionHistory.isNotEmpty()) {
                item {
                    Text(
                        text = "📋  Historial de Sesión",
                        color = Color(0xFF64FFDA),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                }

                itemsIndexed(uiState.sessionHistory) { index, result ->
                    HistoryRow(result = result, isLatest = index == 0)
                }
            }

            item {
                Spacer(modifier = Modifier.height(4.dp))

                Button(
                    onClick = onPlayAgain,
                    modifier = Modifier.fillMaxWidth().height(54.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF64FFDA),
                        contentColor = Color(0xFF0D0D1A)
                    )
                ) {
                    Text("▶  Jugar de Nuevo", fontSize = 16.sp, fontWeight = FontWeight.ExtraBold)
                }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedButton(
                    onClick = onGoHome,
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF8892B0)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF2A2A3E))
                ) {
                    Text("🏠  Inicio", fontSize = 15.sp)
                }
            }
        }
    }
}

@Composable
private fun HistoryRow(result: GameResult, isLatest: Boolean) {
    val accent = if (isLatest) Color(0xFF64FFDA) else Color(0xFF8892B0)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(
                if (isLatest) Color(0xFF16213E) else Color(0xFF16213E).copy(0.5f)
            )
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(if (isLatest) Color(0xFF64FFDA).copy(0.15f) else Color(0xFF2A2A3E))
        ) {
            Text(
                text = "#${result.gameNumber}",
                color = accent,
                fontSize = 11.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "${result.score} pts",
                color = if (isLatest) Color.White else Color(0xFFCCD6F6),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "✅ ${result.correctAnswers}  ❌ ${result.wrongAnswers}",
                color = Color(0xFF8892B0),
                fontSize = 11.sp
            )
        }

        if (isLatest) {
            Spacer(modifier = Modifier.width(8.dp))
            Surface(
                shape = RoundedCornerShape(6.dp),
                color = Color(0xFF64FFDA).copy(0.15f)
            ) {
                Text(
                    text = "HOY",
                    color = Color(0xFF64FFDA),
                    fontSize = 9.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                )
            }
        }
    }
}
