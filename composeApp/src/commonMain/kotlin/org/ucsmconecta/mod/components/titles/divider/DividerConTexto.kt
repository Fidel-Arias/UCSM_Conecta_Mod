package org.ucsmconecta.mod.components.titles.divider

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun DividerConTexto(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.LightGray
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Línea izquierda
        HorizontalDivider(
            modifier = Modifier
                .weight(1f)
                .height(1.dp),
            thickness = DividerDefaults.Thickness,
            color = color
        )

        // Texto centrado
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 8.dp),
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )

        // Línea derecha
        HorizontalDivider(
            modifier = Modifier
                .weight(1f)
                .height(1.dp),
            thickness = DividerDefaults.Thickness,
            color = color
        )
    }
}
