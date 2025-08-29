package org.ucsmconecta.mod.components.body

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.ucsmconecta.mod.components.camera.CameraWithFrame
import org.ucsmconecta.mod.components.info.ImportantMessage
import org.ucsmconecta.mod.components.inputs.CustomSelect
import org.ucsmconecta.mod.components.titles.ConnectionStatus
import org.ucsmconecta.mod.components.titles.WelcomeTitle

@Composable
fun BodyScannerQR() {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {

        Spacer(modifier = Modifier.height(8.dp))
        CameraWithFrame()
        Spacer(modifier = Modifier.height(10.dp))
        ImportantMessage(
            text = "Selecciona todos los campos",
            fontSize = 13
        )
        Spacer(modifier = Modifier.height(10.dp))
        CustomSelect(
            label = "Ubicación",
            opciones =
                listOf(
                    "Seleccione una ubicación",
                    "Auditorio Santa María",
                    "Auditorio William Morris",
                    "Campo Deportivo")
        )
        Spacer(modifier = Modifier.height(8.dp))
        CustomSelect(
            label = "Bloque",
            opciones =
                listOf(
                    "Seleccione un bloque",
                    "9:30 - 10:30: Ciencias de la Computación",
                    "10:30 - 11:30: Inteligencia Artificial",
                    "11:30 - 12:30: Matemáticas Discretas",
                    "12:30 - 13:30: Bases de Datos"
                )
        )
    }
}