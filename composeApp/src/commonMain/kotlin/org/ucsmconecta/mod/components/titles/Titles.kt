package org.ucsmconecta.mod.components.titles

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.delay
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.ucsmconecta.mod.components.icons.getIconWifiOff
import org.ucsmconecta.mod.service.network.getNetworkMonitor
import org.ucsmconecta.mod.ui.theme.CircleOnline
import org.ucsmconecta.mod.ui.theme.ErrorColor
import org.ucsmconecta.mod.ui.theme.PrimaryColor

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
    var showNetworkError by remember { mutableStateOf(false) }

    // Mostrar el modal si se pierde la conexión
    LaunchedEffect(isConnected) {
        if (!isConnected) {
            delay(1000) // Evita falsos negativos al cambiar red
            showNetworkError = true
        } else {
            showNetworkError = false
        }
    }

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
    if (showNetworkError) {
        NetworkErrorModal(onDismiss = { showNetworkError = false })
    }
}

@Composable
fun NetworkErrorModal(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            tonalElevation = 8.dp,
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .wrapContentHeight()
        ) {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = getIconWifiOff(),
                    contentDescription = "Sin conexión",
                    tint = Color.Red,
                    modifier = Modifier.size(60.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Error de conexión",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.Red
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Verifique su conexión a Internet.",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { onDismiss() },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor)
                ) {
                    Text("Entendido", color = Color.White)
                }
            }
        }
    }
}
