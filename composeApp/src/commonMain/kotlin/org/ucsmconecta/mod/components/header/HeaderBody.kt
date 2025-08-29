package org.ucsmconecta.mod.components.header

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import ucsmconectamod.composeapp.generated.resources.AlfaSlabOne
import ucsmconectamod.composeapp.generated.resources.Res
import ucsmconectamod.composeapp.generated.resources.logoApp

@Composable
fun HeaderBody() {
    val logoApp = painterResource(Res.drawable.logoApp)
    val alfaSlabOne = FontFamily(
        Font(Res.font.AlfaSlabOne)
    )
    Box(
        modifier = Modifier
            .height(164.dp)
            .fillMaxWidth()
            .background(color = Color.Transparent),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .background(color = Color.Transparent)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = logoApp,
                contentDescription = "Logo UCSM",
                modifier = Modifier
                    .fillMaxWidth(0.35f) // 40% del ancho de la pantalla
                    .fillMaxHeight(),
                contentScale = ContentScale.Fit // Ajusta la imagen al espacio disponible
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "UcsmConecta",
                    fontSize = 26.sp,
                    color = Color.White,
                    fontFamily = alfaSlabOne
                )
                Spacer(
                    modifier = Modifier
                        .height(3.dp)
                        .fillMaxWidth(0.8f)
                        .background(color = Color.White)
                )
                Text(
                    text = "Control de Asistencia",
                    fontSize = 16.sp,
                    color = Color.White,
                    fontFamily = alfaSlabOne
                )
            }

        }
    }
}