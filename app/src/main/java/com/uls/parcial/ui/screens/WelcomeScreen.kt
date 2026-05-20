package com.uls.parcial.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uls.parcial.ui.components.RuleItem
import com.uls.parcial.ui.theme.DarkBackGround
import com.uls.parcial.ui.theme.onPrimary
import com.uls.parcial.ui.theme.primary

@Composable
fun WelcomeScreen(onStartGame: () -> Unit) {

    val colors = listOf(
        Color(0xFFE53935), Color(0xFF1E88E5), Color(0xFF43A047),
        Color(0xFFFDD835), Color(0xFFFB8C00), Color(0xFF8E24AA)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackGround),
        contentAlignment = Alignment.Center
    ) {
        colors.forEachIndexed { index, color ->
            val offsetX = ((index % 3) - 1) * 140f
            val offsetY = ((index / 3) - 0.5f) * 300f
            Box(
                modifier = Modifier
                    .offset(offsetX.dp, offsetY.dp)
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(color.copy(alpha = 0.18f))
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(24.dp)
                .clip(RoundedCornerShape(32.dp))
                .background(Color(0xFF16213E).copy(alpha = 0.95f))
                .padding(36.dp)
        ) {

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(bottom = 24.dp)
            ) {
                colors.forEach { color ->
                    Box(
                        modifier = Modifier
                            .size(18.dp)
                            .clip(CircleShape)
                            .background(color)
                    )
                }
            }

            Text(
                text = "Juego de Colores",
                color = Color.White,
                fontSize = 36.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 2.sp
            )

            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color(0xFF0F3460).copy(alpha = 0.6f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "📋  Reglas del Juego",
                        color = primary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    RuleItem("🎯", "Se mostrará un color en pantalla")
                    RuleItem("👆", "Presiona el botón con el nombre correcto")
                    RuleItem("✅", "+10 puntos por acierto")
                    RuleItem("❌", "-5 puntos por error")
                    RuleItem("⏱️", "Tienes 30 segundos para lograr el mayor puntaje")
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            Button(
                onClick = onStartGame,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = primary,
                    contentColor = onPrimary
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
            ) {
                Text(
                    text = "▶ Iniciar Juego",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp
                )
            }
        }
    }
}