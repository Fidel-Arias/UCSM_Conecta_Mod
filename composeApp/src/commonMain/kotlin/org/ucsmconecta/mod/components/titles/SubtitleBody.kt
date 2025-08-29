package org.ucsmconecta.mod.components.titles

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

@Composable
fun SubtitleBody(subtitle: String, fontFamily: FontFamily, fontSize: TextUnit) {
    Text(
        text = subtitle,
        fontSize = fontSize,
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        fontWeight = FontWeight.Bold,
        fontFamily = fontFamily,
        textAlign = TextAlign.Center
    )
}