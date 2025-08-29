package org.ucsmconecta.mod.components.header

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import ucsmconectamod.composeapp.generated.resources.Res
import ucsmconectamod.composeapp.generated.resources.logoJinis2025

@Composable
fun HeaderBodyLogin() {
    val logoJinis = painterResource(Res.drawable.logoJinis2025)
    Box(
        modifier = Modifier
            .height(164.dp)
            .fillMaxWidth()
            .background(color = Color.Transparent),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = logoJinis,
            contentDescription = "Logo JINIS",
            modifier = Modifier
                .fillMaxWidth(1f) // 90% del ancho de la pantalla
                .fillMaxHeight(),
            contentScale = ContentScale.Fit // Ajusta la imagen al espacio disponible
        )
    }
}