package org.ucsmconecta.mod.components.titles

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.ucsmconecta.mod.service.network.getNetworkMonitor
import org.ucsmconecta.mod.ui.theme.CircleOnline
import org.ucsmconecta.mod.ui.theme.ErrorColor

@Composable
fun WelcomeTitle(
    text: String
) {
    Text(
        text = "Bienvenido, $text",
        fontFamily = FontFamily.SansSerif,
        fontSize = 28.sp,
        color = Color.Black,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
    )
}

@Composable
fun ConnectionStatus() {
    val networkMonitor = remember { getNetworkMonitor() }
    val isConnected by networkMonitor.isConnected.collectAsState(initial = false)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(
                    if (isConnected) CircleOnline else ErrorColor,
                    CircleShape
                )
        )

        Text(
            text = if (isConnected) "En línea" else "Desconectado",
            fontFamily = FontFamily.SansSerif,
            color = Color.Black,
            modifier = Modifier
                .padding(start = 6.dp)
        )
    }
}