package org.ucsmconecta.mod.components.body

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.ucsmconecta.mod.components.camera.CameraWithFrame
import org.ucsmconecta.mod.components.info.ImportantMessage
import org.ucsmconecta.mod.components.inputs.CustomSelect
import org.ucsmconecta.mod.components.message.MessageModal
import org.ucsmconecta.mod.data.model.bloques.DataResponseBloque
import org.ucsmconecta.mod.data.model.ubicacion.DataResponseUbicacion
import org.ucsmconecta.mod.data.model.uistate.UiState
import org.ucsmconecta.mod.ui.theme.PrimaryColor
import org.ucsmconecta.mod.viewModel.ScannerViewModel

@Composable
fun BodyScannerQR(
    ubicacionState: UiState<List<DataResponseUbicacion>>,
    bloqueState: UiState<List<DataResponseBloque>>,
    scannerViewModel: ScannerViewModel,
    ) {
    val scrollState = rememberScrollState()
    var selectedUbicacion by remember { mutableStateOf<DataResponseUbicacion?>(null) }
    var selectedBloque by remember { mutableStateOf<DataResponseBloque?>(null) }

    val mensajeAsistencia by scannerViewModel.mensajeAsistencia.collectAsState()
    val tipoMensaje by scannerViewModel.tipoMensaje.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier .padding(14.dp)
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            if (selectedBloque != null) {
                CameraWithFrame(
                    scannerViewModel = scannerViewModel,
                    bloqueId = selectedBloque?.id,
                    congresoCod = selectedBloque?.dia!!.congreso.codigo,
                )

                Spacer(modifier = Modifier.height(10.dp)) }
            else {
                ImportantMessage(
                    text = "Selecciona un bloque para activar la cámara",
                    fontSize = 13 )
            }

            Spacer(modifier = Modifier.height(10.dp))

            SelectUbicacion(ubicacionState) { selectedUbicacion = it }

            Spacer(modifier = Modifier.height(8.dp))

            SelectBloque(bloqueState, selectedUbicacion) { selectedBloque = it }
        }

        // Mostrar modal si hay mensaje (success o error)
        mensajeAsistencia?.let { msg ->
            MessageModal(
                message = msg,
                isError = tipoMensaje == false,
                onDismiss = { scannerViewModel.clearMensaje() }
            )
        }
    }
}

@Composable
fun SelectUbicacion(
    ubicacionState: UiState<List<DataResponseUbicacion>>,
    onUbicacionSeleccionada: (DataResponseUbicacion?) -> Unit
) {
    when (ubicacionState) {
        is UiState.Loading -> CircularProgressIndicator(color = PrimaryColor)
        is UiState.Success -> {
            val ubicaciones = ubicacionState.data
            if (ubicaciones.isEmpty()) {
                CustomSelect(
                    label = "Ubicación",
                    opciones = listOf("No hay ubicaciones presentes"),
                    onOptionSelected = {}
                )
            } else {
                CustomSelect(
                    label = "Ubicación",
                    opciones = listOf("Seleccione una ubicación") + ubicaciones.map { it.nombre },
                    onOptionSelected = { selected ->
                        onUbicacionSeleccionada(ubicaciones.find { it.nombre == selected })
                    }
                )
            }
        }
        is UiState.Error -> CustomSelect(
            label = "Ubicación",
            opciones = listOf(ubicacionState.message),
            onOptionSelected = {}
        )
        else -> Unit
    }
}

@Composable
fun SelectBloque(
    bloqueState: UiState<List<DataResponseBloque>>,
    selectedUbicacion: DataResponseUbicacion?,
    onBloqueSeleccionado: (DataResponseBloque?) -> Unit
) {
    when (bloqueState) {
        is UiState.Loading -> CircularProgressIndicator(color = ProgressIndicatorDefaults.circularColor)
        is UiState.Success -> {
            val bloques = bloqueState.data
            if (bloques.isEmpty()) {
                CustomSelect(
                    label = "Bloque",
                    opciones = listOf("No hay bloques presentes"),
                    onOptionSelected = {  }
                )
            } else {
                CustomSelect(
                    label = "Bloque",
                    opciones = listOf("Seleccione un bloque") + bloques
                        .filter { it.ubicacion.id == selectedUbicacion?.id }
                        .map {
                        "${it.horaInicial} - ${it.horaFinal}: ${it.ponencia.nombre}"
                    },
                    onOptionSelected = { selected ->
                        onBloqueSeleccionado(
                            bloques.find {
                                "${it.horaInicial} - ${it.horaFinal}: ${it.ponencia.nombre}" == selected
                            }
                        )
                    }
                )
            }
        }
        is UiState.Error -> CustomSelect(
            label = "Bloque",
            opciones = listOf(bloqueState.message),
            onOptionSelected = {  }
        )
        else -> Unit
    }
}
