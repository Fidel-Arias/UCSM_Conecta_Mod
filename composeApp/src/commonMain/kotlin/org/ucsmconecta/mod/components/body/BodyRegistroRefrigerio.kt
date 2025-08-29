package org.ucsmconecta.mod.components.body

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.ucsmconecta.mod.components.button.CustomButton
import org.ucsmconecta.mod.components.camera.CameraWithFrame
import org.ucsmconecta.mod.components.info.ImportantMessage
import org.ucsmconecta.mod.components.inputs.CustomSelect
import org.ucsmconecta.mod.components.inputs.InputTextField
import org.ucsmconecta.mod.components.titles.divider.DividerConTexto

@Composable
fun BodyRegistroRefrigerio() {
    var input by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        CameraWithFrame()
        Spacer(Modifier.height(10.dp))
        ImportantMessage(
            text = "Esta sección es para los refrigerios de los participantes.",
            fontSize = 13,
        )
        Spacer(Modifier.height(10.dp))
        CustomSelect(
            label = "# de Refrigerio",
            opciones = listOf("Selecciona una opción","Refrigerio 1", "Refrigerio 2", "Refrigerio 3", "Refrigerio 4"),
            ancho = .8f
        )
        Spacer(Modifier.height(10.dp))
        DividerConTexto(
            text = "o",
            modifier = Modifier.padding(horizontal = 16.dp),
            color = Color.LightGray
        )
        Spacer(Modifier.height(12.dp))
        InputTextField(
            value = input,
            ancho = .8f,
            onValueChange = { input = it },
            label = "DNI o Pasaporte",
            textFieldColors = TextFieldDefaults.colors(),
            isRequired = true,
            maxLength = 10,
            isError = input.length < 8,
            errorMessage = "Ingrese al menos 12 caracteres"
        )
        Spacer(Modifier.height(12.dp))
        CustomButton(
            text = "Marcar",
            anchoButton = .8f,
            onClick = { /* Acción al hacer clic en el botón */ }
        )
    }
}