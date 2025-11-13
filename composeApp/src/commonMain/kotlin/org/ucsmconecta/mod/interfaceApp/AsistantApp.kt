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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import org.ucsmconecta.mod.components.body.BodyRegistroManual
import org.ucsmconecta.mod.components.body.BodyRegistroRefrigerio
import org.ucsmconecta.mod.components.body.BodyScannerQR
import org.ucsmconecta.mod.components.navbar.NavBar
import org.ucsmconecta.mod.components.refresh.RefreshableContent
import org.ucsmconecta.mod.components.titles.ConnectionStatus
import org.ucsmconecta.mod.components.titles.TextConBorde
import org.ucsmconecta.mod.components.titles.WelcomeTitle
import org.ucsmconecta.mod.data.model.colaborador.DataResposeColaboradorCongreso
import org.ucsmconecta.mod.data.model.uistate.UiState
import org.ucsmconecta.mod.data.network.ApiService
import org.ucsmconecta.mod.data.network.createHttpClient
import org.ucsmconecta.mod.data.repository.ColaboradorRepository
import org.ucsmconecta.mod.infra.errors.UnauthorizedException
import org.ucsmconecta.mod.ui.theme.ErrorColor
import org.ucsmconecta.mod.ui.theme.PrimaryColor
import org.ucsmconecta.mod.utils.TokenStorage
import org.ucsmconecta.mod.utils.backLogin
import org.ucsmconecta.mod.viewModel.ScannerViewModel
import org.ucsmconecta.mod.viewModel.BloqueViewModel
import org.ucsmconecta.mod.viewModel.ColaboradorCongresoViewModel
import org.ucsmconecta.mod.viewModel.ParticipanteViewModel
import org.ucsmconecta.mod.viewModel.TipoParticipanteViewModel
import org.ucsmconecta.mod.viewModel.UbicacionViewModel

@Composable
fun AsistantApp(
    carrera: String,
    token: TokenStorage
) {
    var selectedItemNavbar by remember { mutableStateOf(0) }
    val coroutineScope = rememberCoroutineScope()
    var congresoColaborador by remember { mutableStateOf("") }
    var escuelaCodigo by remember { mutableStateOf("") }

    // Conexion con el cliente
    val client = createHttpClient()

    // Llamando a repository
    val repository = remember { ColaboradorRepository(ApiService(client, token)) }

    // Cargando la vista del viewModel
    val colaboradorCongresoViewModel = viewModel { ColaboradorCongresoViewModel(repository) }
    val ubicacionViewModel = viewModel { UbicacionViewModel(repository) }
    val bloqueViewModel = viewModel { BloqueViewModel(repository) }
    val scannerViewModel = viewModel { ScannerViewModel(repository) }
    val participanteViewModel = viewModel { ParticipanteViewModel(repository) }
    val tipoParticipanteViewModel = viewModel { TipoParticipanteViewModel(repository) }

    // Estado del ViewModel
    val colaboradorState by colaboradorCongresoViewModel.uiState.collectAsState()
    val ubicacionState by ubicacionViewModel.uiState.collectAsState()
    val bloqueState by bloqueViewModel.uiState.collectAsState()
    val tipoParticipanteState by tipoParticipanteViewModel.uiState.collectAsState()

    var firstLoad by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        if (firstLoad) {
            val tokenValue = token.getToken()
            if (tokenValue == null) {
                backLogin()
            } else {
                try {
                    recargarDatos(colaboradorCongresoViewModel, ubicacionViewModel, bloqueViewModel, tipoParticipanteViewModel, token)
                } catch (e: UnauthorizedException) {
                    // Token expirado
                    backLogin()
                } catch (e: Exception) {
                    // Otro error
                    println("Error al cargar datos: ${e.message}")
                }
            }
            firstLoad = false
        }
    }

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
        when(colaboradorState) {
            is UiState.Loading -> CircularProgressIndicator(color = PrimaryColor)
            is UiState.Success -> {
                val data = (colaboradorState as UiState.Success<DataResposeColaboradorCongreso>).data
                val nombreFormateado = data.colaborador.nombres.trim().split(" ")[0]

                congresoColaborador = data.congreso.codigo
                escuelaCodigo = data.colaborador.escuelaProfesional.codigo

                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                ) {
                    //Secciones comunes
                    WelcomeTitle(nombreFormateado)
                    Spacer(modifier = Modifier.height(8.dp))
                    ConnectionStatus()

                    RefreshableContent(
                        selectedItem = selectedItemNavbar,
                        onRefresh = {
                            coroutineScope.launch {
                                recargarDatos(colaboradorCongresoViewModel, ubicacionViewModel, bloqueViewModel, tipoParticipanteViewModel, token)
                            }
                        }
                    ) {
                        when (selectedItemNavbar) {
                            0 -> BodyScannerQR(
                                ubicacionState = ubicacionState,
                                bloqueState = bloqueState,
                                scannerViewModel = scannerViewModel
                            )
                            1 -> BodyRegistroManual(
                                scannerViewModel = scannerViewModel,
                                bloqueState = bloqueState,
                                congresoCod = congresoColaborador,
                                escuelaCod = escuelaCodigo,
                                participanteViewModel = participanteViewModel,
                                ubicacionState = ubicacionState,
                                tipoParticipanteState = tipoParticipanteState
                            )
                            2 -> BodyRegistroRefrigerio(
                                scannerViewModel = scannerViewModel,
                                congresoCod = congresoColaborador
                            )
                        }
                    }
                }
            }
            is UiState.Error -> Text("Error al cargar colaborador", color = ErrorColor)
            else -> Unit
        }
    }
}

suspend fun recargarDatos(
    colaboradorCongresoViewModel: ColaboradorCongresoViewModel,
    ubicacionViewModel: UbicacionViewModel,
    bloqueViewModel: BloqueViewModel,
    tipoParticipanteViewModel: TipoParticipanteViewModel,
    token: TokenStorage
) {
    val id = token.getId()
    colaboradorCongresoViewModel.cargarDatosColaborador(id)
    ubicacionViewModel.cargarTodasLasUbicaciones()
    bloqueViewModel.cargarTodosLosBloquesPorDia()
    tipoParticipanteViewModel.cargarDatosTipoParticipantes()
}
