package org.ucsmconecta.mod.components.body

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.ucsmconecta.mod.components.button.CustomButtonWithIcon
import org.ucsmconecta.mod.components.icons.getIconKeyboard
import org.ucsmconecta.mod.components.icons.getIconUserAdd
import org.ucsmconecta.mod.components.info.ImportantMessage
import org.ucsmconecta.mod.ui.theme.ErrorColor
import org.ucsmconecta.mod.ui.theme.LightGreen

@Composable
fun BodyRegistroManual() {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        CustomButtonWithIcon(
            text = "Registrar DNI o Pasaporte",
            icon = getIconKeyboard(),
            colorButton = LightGreen,
            descriptionIcon = "Keyboard",
            sizeIcon = 40,
            onClick = { /* TODO: Implement registration logic */ },
            bold = true,
            fontSize = 22,
            isRow = false,
            roundedCorner = 20
        )
        Spacer(Modifier.height(8.dp))
        ImportantMessage(
            text = "Selecciona esta opción para registrar manualmente",
            fontSize = 13
        )
        Spacer(Modifier.height(12.dp))
        CustomButtonWithIcon(
            text = "Registrar Participante",
            icon = getIconUserAdd(),
            colorButton = ErrorColor,
            descriptionIcon = "Person Add",
            sizeIcon = 40,
            onClick = { /* TODO: Implement registration logic */ },
            bold = true,
            fontSize = 22,
            isRow = false,
            roundedCorner = 20
        )
        Spacer(Modifier.height(8.dp))
        ImportantMessage(
            text = "Selecciona esta opción para registrar manualmente",
            fontSize = 13
        )
    }
}