package org.ucsmconecta.mod.components.body

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.ucsmconecta.mod.components.card.cardCarreras
import org.ucsmconecta.mod.components.header.HeaderBody
import org.ucsmconecta.mod.components.titles.SubtitleBody
import ucsmconectamod.composeapp.generated.resources.ReadexProBold
import ucsmconectamod.composeapp.generated.resources.Res
import ucsmconectamod.composeapp.generated.resources.ucsmIngIndustrial
import ucsmconectamod.composeapp.generated.resources.ucsmIngSistemas

@Composable
fun CuerpoCarreras(
    scope: CoroutineScope,
    onCardSelected: () -> Unit
) {
    // Lista de imágenes de las carreras
    val listImages = listOf(
        painterResource(Res.drawable.ucsmIngSistemas),
    )
    val READEX_PRO_BOLD = FontFamily(
        Font(Res.font.ReadexProBold)
    ) // Fuente Readex Pro Bold
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        SubtitleBody(
            subtitle = "MODERADORES",
            fontFamily = READEX_PRO_BOLD,
            fontSize = 24.sp
        ) // Título de la sección
        // LazyColumn para mostrar las tarjetas de carreras con scroll
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(6.dp), // Espacio alrededor de los elementos
            verticalArrangement = Arrangement.spacedBy(15.dp), // Espacio entre los elementos
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            items(listImages.size) { i ->
                cardCarreras(
                    // Se usa when para asignar el nombre de la carrera según el índice
                    carrera = when (i) {
                        0 -> "Escuela Profesional de Ingeniería de Sistemas"
                        else -> "Carrera Desconocida"
                    },
                    image = listImages[i],
                    onclick = {
                        scope.launch { // Acción al hacer clic en la tarjeta
                            onCardSelected()  // notificar para animar
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun BodyModeradores(
    scope: CoroutineScope,
    onCardSelected: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Transparent)
            .padding(10.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderBody()
        CuerpoCarreras(
            scope,
            onCardSelected)
    }
}