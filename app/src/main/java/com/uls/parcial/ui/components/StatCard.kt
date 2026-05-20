package com.uls.parcial.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StatCard(label: String, value: String, accent: Color, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF0F3460).copy(0.5f))
            .padding(vertical = 12.dp, horizontal = 6.dp)
    ) {
        Text(text = value, color = accent, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
        Spacer(modifier = Modifier.height(3.dp))
        Text(text = label, color = Color(0xFF8892B0), fontSize = 9.sp, textAlign = TextAlign.Center, lineHeight = 12.sp)
    }
}
