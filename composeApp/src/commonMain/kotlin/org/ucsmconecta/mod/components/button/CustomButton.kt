package org.ucsmconecta.mod.components.button

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import org.ucsmconecta.mod.ui.theme.PrimaryColor

@Composable
fun CustomButton(
    onClick: () -> Unit,
    text: String,
    anchoButton: Float = 1f
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(anchoButton),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = PrimaryColor
            )
        ) {
            Text(
                text = text,
                fontSize = 20.sp
            )
        }
    }
}