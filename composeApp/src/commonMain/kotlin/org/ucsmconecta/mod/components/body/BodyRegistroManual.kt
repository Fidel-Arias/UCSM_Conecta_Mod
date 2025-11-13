package org.ucsmconecta.mod.components.body

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import org.ucsmconecta.mod.components.button.CustomButtonWithIcon
import org.ucsmconecta.mod.components.icons.getIconKeyboard
import org.ucsmconecta.mod.components.icons.getIconUserAdd
import org.ucsmconecta.mod.components.info.ImportantMessage
import org.ucsmconecta.mod.components.inputs.CustomSelect
import org.ucsmconecta.mod.components.message.MessageModal
import org.ucsmconecta.mod.data.model.bloques.DataResponseBloque
import org.ucsmconecta.mod.data.model.participante.DataTipoParticipanteResponse
import org.ucsmconecta.mod.data.model.ubicacion.DataResponseUbicacion
import org.ucsmconecta.mod.data.model.uistate.UiState
import org.ucsmconecta.mod.ui.theme.ErrorColor
import org.ucsmconecta.mod.ui.theme.LightGreen
import org.ucsmconecta.mod.ui.theme.PrimaryColor
import org.ucsmconecta.mod.ui.theme.SecondaryColor
import org.ucsmconecta.mod.viewModel.ParticipanteViewModel
import org.ucsmconecta.mod.viewModel.ScannerViewModel

@Composable
fun BodyRegistroManual(
    ubicacionState: UiState<List<DataResponseUbicacion>>,
    bloqueState: UiState<List<DataResponseBloque>>,
    congresoCod: String,
    scannerViewModel: ScannerViewModel,
    participanteViewModel: ParticipanteViewModel,
    tipoParticipanteState: UiState<List<DataTipoParticipanteResponse>>,
    escuelaCod: String
) {
    val scrollState = rememberScrollState()
    var showDialogDni by remember { mutableStateOf(false) }
    var showDialogRegParticipante by remember { mutableStateOf(false) }

    val mensajeAsistencia by scannerViewModel.mensajeAsistencia.collectAsState()
    val tipoMensaje by scannerViewModel.tipoMensaje.collectAsState()

    val mensajeRegistro by participanteViewModel.mensajeRegistro.collectAsState()
    val tipoMensajeRegistro by participanteViewModel.tipoMensaje.collectAsState()
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
            onClick = { showDialogDni = true },
            bold = true,
            fontSize = 22,
            isRow = false,
            roundedCorner = 20
        )
        Spacer(Modifier.height(8.dp))
        ImportantMessage(
            text = "Selecciona esta opción para registrar manualmente por DNI o Pasaporte",
            fontSize = 13
        )
        Spacer(Modifier.height(12.dp))
        CustomButtonWithIcon(
            text = "Registrar Participante",
            icon = getIconUserAdd(),
            colorButton = ErrorColor,
            descriptionIcon = "Person Add",
            sizeIcon = 40,
            onClick = { showDialogRegParticipante = true },
            bold = true,
            fontSize = 22,
            isRow = false,
            roundedCorner = 20
        )
        Spacer(Modifier.height(8.dp))
        ImportantMessage(
            text = "Selecciona esta opción para registrar manualmente un participante",
            fontSize = 13
        )
    }
    // Mostrar el modal cuando se pulse el botón
    RegistroDniDialog(
        showDialog = showDialogDni,
        bloqueState = bloqueState,
        ubicacionState = ubicacionState,
        onDismiss = { showDialogDni = false },
        onSubmit = { documento, bloqueId ->
            showDialogDni = false
            scannerViewModel.registrarAsistencia(
                documentoParticipante = documento,
                bloqueId = bloqueId,
                congresoCod = congresoCod
            )
        }
    )

    RegistroParticipanteDialog(
        showDialog = showDialogRegParticipante,
        onDismiss = { showDialogRegParticipante = false },
        tipoParticipanteState = tipoParticipanteState,
        onSubmit = { nombres, apellidos, email, documento, tipoParticipanteId ->
            showDialogRegParticipante = false
            val apellidosSeparado = apellidos.split(" ")
            val apPaterno = apellidosSeparado[0]
            val apMaterno = apellidosSeparado[1]
            participanteViewModel.registrarParticipante(
                nombres = nombres,
                apPaterno = apPaterno,
                apMaterno = apMaterno,
                numDocumento = documento,
                email = email,
                tipoParticipanteId = tipoParticipanteId,
                congresoCodigo = congresoCod,
                escuelaCodigo = escuelaCod
            )
        }
    )

    // Mostrar mensaje de éxito o error
    mensajeAsistencia?.let { msg ->
        MessageModal(
            message = msg,
            isError = tipoMensaje == false,
            onDismiss = { scannerViewModel.clearMensaje() }
        )
    }

    mensajeRegistro?.let { msg ->
        MessageModal(
            message = msg,
            isError = tipoMensajeRegistro == false,
            onDismiss = { participanteViewModel.clearMensaje() }
        )
    }
}

@Composable
fun RegistroDniDialog(
    showDialog: Boolean,
    bloqueState: UiState<List<DataResponseBloque>>,
    ubicacionState: UiState<List<DataResponseUbicacion>>,
    onDismiss: () -> Unit,
    onSubmit: (String, Long) -> Unit
) {
    if (showDialog) {
        Dialog(onDismissRequest = { onDismiss() }) {
            Surface(
                tonalElevation = 10.dp,
                modifier = Modifier
                    .fillMaxWidth(0.98f)
                    .wrapContentHeight()
                    .background(Color.White)
            ) {
                var documento by remember { mutableStateOf("") }
                var selectedBloque by remember { mutableStateOf<DataResponseBloque?>(null) }
                var selectedUbicacion by remember { mutableStateOf<DataResponseUbicacion?>(null) }

                Column(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Registro Manual",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF00796B)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Ingrese el número de documento, seleccione el bloque y la ubicación",
                        fontSize = 14.sp,
                        color = SecondaryColor,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Campo de texto
                    OutlinedTextField(
                        value = documento,
                        onValueChange = { if (it.length <= 12) documento = it },
                        label = { Text("DNI o Pasaporte") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Selector de Ubicacion
                    when (ubicacionState) {
                        is UiState.Loading -> CircularProgressIndicator(color = PrimaryColor)
                        is UiState.Success -> {
                            val ubicaciones = ubicacionState.data
                            if (ubicaciones.isEmpty()) {
                                CustomSelect(
                                    label = "Ubicación",
                                    opciones = listOf("No hay ubicaciones presentes"),
                                    onOptionSelected = {  }
                                )
                            } else {
                                CustomSelect(
                                    label = "Ubicación",
                                    opciones = listOf("Seleccione una ubicación") + ubicaciones.map { it.nombre },
                                    onOptionSelected = { selectedNombre ->
                                        selectedUbicacion = ubicaciones.find { it.nombre == selectedNombre }
                                    }
                                )
                            }
                        }
                        is UiState.Error -> CustomSelect(
                            label = "Ubicación",
                            opciones = listOf(ubicacionState.message),
                            onOptionSelected = {  }
                        )
                        else -> Unit
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Selector de bloque
                    when (bloqueState) {
                        is UiState.Loading -> CircularProgressIndicator(color = SecondaryColor)
                        is UiState.Success -> {
                            val bloques = bloqueState.data
                            if (bloques.isEmpty()) {
                                CustomSelect(
                                    label = "Bloque",
                                    opciones = listOf("No hay bloques disponibles"),
                                    onOptionSelected = { }
                                )
                            } else {
                                CustomSelect(
                                    label = "Bloque",
                                    opciones = listOf("Seleccione un bloque") + bloques
                                        .filter { it.ubicacion.id == selectedUbicacion?.id }
                                        .map {
                                        "${it.horaInicial} - ${it.horaFinal}: ${it.ponencia.nombre}"
                                    },
                                    onOptionSelected = { selectedText ->
                                        selectedBloque = if (selectedText != "Seleccione un bloque") {
                                            bloques.find {
                                                "${it.horaInicial} - ${it.horaFinal}: ${it.ponencia.nombre}" == selectedText
                                            }
                                        } else null
                                    }
                                )
                            }
                        }
                        is UiState.Error -> CustomSelect(
                            label = "Bloque",
                            opciones = listOf(bloqueState.message),
                            onOptionSelected = { }
                        )
                        else -> Unit
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = { onDismiss() },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Cancelar", color = Color.DarkGray)
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Button(
                            onClick = {
                                if (documento.isNotBlank() && selectedBloque != null) {
                                    onSubmit(documento, selectedBloque!!.id)
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Registrar", color = Color.White)
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun RegistroParticipanteDialog(
    showDialog: Boolean,
    tipoParticipanteState: UiState<List<DataTipoParticipanteResponse>>,
    onDismiss: () -> Unit,
    onSubmit: (String, String, String, String, Long) -> Unit
) {
    if (showDialog) {
        Dialog(onDismissRequest = { onDismiss() })
        {
            Surface(
                tonalElevation = 10.dp,
                modifier = Modifier
                    .fillMaxWidth(0.98f)
                    .wrapContentHeight()
                    .background(Color.White)
            ) {
                var nombres by remember { mutableStateOf("") }
                var apellidos by remember { mutableStateOf("") }
                var documento by remember { mutableStateOf("") }
                var email by remember { mutableStateOf("") }
                var tipoParticipanteSelected by remember { mutableStateOf<DataTipoParticipanteResponse?>(null) }

                // Estado de validación de apellidos
                var apellidoError by remember { mutableStateOf<String?>(null) }

                Column(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Registro Manual",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF00796B)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Ingrese los siguientes campos para registrar al participante",
                        fontSize = 14.sp,
                        color = SecondaryColor,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Campo de nombres
                    OutlinedTextField(
                        value = nombres,
                        onValueChange = { nombres = it.uppercase() },
                        label = { Text("Nombres") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Campo de apellidos
                    OutlinedTextField(
                        value = apellidos,
                        onValueChange = {
                            apellidos = it.uppercase()
                            val parts = apellidos.trim().split(" ").filter { it.isNotEmpty() }
                            apellidoError = if (parts.size < 2) "Ingrese ambos apellidos" else null
                        },
                        label = { Text("Apellidos") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        modifier = Modifier.fillMaxWidth()
                    )

                    if (apellidoError != null) {
                        Text(
                            text = apellidoError ?: "",
                            color = Color.Red,
                            fontSize = 12.sp,
                            modifier = Modifier
                                .align(Alignment.Start)
                                .padding(start = 8.dp, top = 2.dp)
                        )
                    }

                    // Campo de documento
                    OutlinedTextField(
                        value = documento,
                        onValueChange = { if (it.length <= 12) documento = it },
                        label = { Text("DNI o Pasaporte") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Campo de email
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    // Selector de Tipos de Participante
                    when (tipoParticipanteState) {
                        is UiState.Loading -> CircularProgressIndicator(color = PrimaryColor)
                        is UiState.Success -> {
                            val tipoParticipantes = tipoParticipanteState.data
                            if (tipoParticipantes.isEmpty()) {
                                CustomSelect(
                                    label = "Tipo de Participante",
                                    opciones = listOf("No hay tipos"),
                                    onOptionSelected = {  }
                                )
                            } else {
                                CustomSelect(
                                    label = "Tipo de Participante",
                                    opciones = tipoParticipantes.map { it.descripcion },
                                    onOptionSelected = { selectedNombre ->
                                        tipoParticipanteSelected = tipoParticipantes.find { it.descripcion == selectedNombre }
                                    }
                                )
                            }
                        }
                        is UiState.Error -> CustomSelect(
                            label = "Tipo de Participante",
                            opciones = listOf(tipoParticipanteState.message),
                            onOptionSelected = {  }
                        )
                        else -> Unit
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = { onDismiss() },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Cancelar", color = Color.DarkGray)
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Button(
                            onClick = {
                                if (nombres.isNotBlank() && apellidos.isNotBlank() && email.isNotBlank() && documento.isNotBlank() && tipoParticipanteSelected != null) {
                                    if (apellidos.split(" ").size == 2)
                                        onSubmit(nombres, apellidos, email, documento, tipoParticipanteSelected!!.id)

                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Registrar", color = Color.White)
                        }
                    }
                }
            }
        }
    }
}

