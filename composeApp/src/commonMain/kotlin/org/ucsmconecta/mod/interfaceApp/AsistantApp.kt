package org.ucsmconecta.mod.interfaceApp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.ucsmconecta.mod.components.body.BodyRegistroManual
import org.ucsmconecta.mod.components.body.BodyRegistroRefrigerio
import org.ucsmconecta.mod.components.body.BodyScannerQR
import org.ucsmconecta.mod.components.camera.CameraWithFrame
import org.ucsmconecta.mod.components.info.ImportantMessage
import org.ucsmconecta.mod.components.inputs.CustomSelect
import org.ucsmconecta.mod.components.navbar.NavBar
import org.ucsmconecta.mod.components.titles.ConnectionStatus
import org.ucsmconecta.mod.components.titles.TextConBorde
import org.ucsmconecta.mod.components.titles.WelcomeTitle
import org.ucsmconecta.mod.ui.theme.PrimaryColor

@Composable
fun AsistantApp(carrera: String) {
    var selectedItemNavbar by remember { mutableStateOf(0) }
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding() // respeta la barra de estado
                    .background(color = PrimaryColor)
                    .padding(vertical = 8.dp) // espacio interno del topbar
            ) {
                TextConBorde(carrera)
            }
        },
        bottomBar = {
            NavBar(
                selectedItemNavbar = selectedItemNavbar,
                onItemSelected = { selectedItemNavbar = it }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            //Secciones comunes
            WelcomeTitle("Fidel")
            Spacer(modifier = Modifier.height(8.dp))
            ConnectionStatus()

            // Cambia el body según el navbar seleccionado
            when (selectedItemNavbar) {
                0 -> BodyScannerQR() // Escaner QR
                1 -> BodyRegistroManual() // Registro Manual
                2 -> BodyRegistroRefrigerio() // Registro Refrigerio
            }
        }

    }
}