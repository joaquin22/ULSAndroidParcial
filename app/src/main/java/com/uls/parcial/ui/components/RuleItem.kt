package com.uls.parcial.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RuleItem(icon: String, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Text(text = icon, fontSize = 16.sp, modifier = Modifier.width(28.dp))
        Text(
            text = text,
            color = Color(0xFFCCD6F6),
            fontSize = 13.sp,
            lineHeight = 18.sp
        )
    }
}