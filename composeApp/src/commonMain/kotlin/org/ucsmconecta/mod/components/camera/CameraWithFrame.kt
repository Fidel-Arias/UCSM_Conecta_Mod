package org.ucsmconecta.mod.components.camera

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import kotlinx.serialization.json.Json
import org.ucsmconecta.mod.data.model.qr.QRCode
import org.ucsmconecta.mod.service.settings.openAppSettings
import org.ucsmconecta.mod.viewModel.ScannerViewModel
import org.ucsmconecta.mod.viewModel.RefrigerioViewModel

@Composable
fun CameraWithFrame(
    scannerViewModel: ScannerViewModel,
    bloqueId: Long?,
    congresoCod: String,
) {
    val marcoSize = 250.dp
    val isCameraActive = remember { mutableStateOf(false) }
    val isPermanentlyDenied = remember { mutableStateOf(false) }

    // Evitar reenviar el mismo QR varias veces
    var lastScannedCode by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(isPermanentlyDenied.value) {
        if (isPermanentlyDenied.value) {
            openAppSettings()
        }
    }

    Box(
        modifier = Modifier.wrapContentSize(),
        contentAlignment = Alignment.Center
    ) {
        // Vista Previa de la camara
        CameraPreview(
            reductionFactor = 0.3f,
            onCameraStatusChanged = { active, permanentlyDenied ->
                isCameraActive.value = active
                isPermanentlyDenied.value = permanentlyDenied
            },
            onQrDetected = { qr ->
                // Evita reprocesar el mismo código muchas veces
                if (qr != lastScannedCode) {
                    lastScannedCode = qr

                    val json = Json { ignoreUnknownKeys = true }
                    // Limpiar posible envoltura con comillas
                    val cleaned = qr.trim().let { s ->
                        // si empieza y termina con comillas dobles o simples, las quitamos
                        if ((s.startsWith("\"") && s.endsWith("\"")) || (s.startsWith("'") && s.endsWith("'"))) {
                            s.substring(1, s.length - 1)
                        } else s
                    }
                    try {
                        val data = json.decodeFromString<QRCode>(cleaned)
                        if (bloqueId != null) {
                            scannerViewModel.registrarAsistencia(
                                documentoParticipante = data.numDocumento,
                                bloqueId = bloqueId,
                                congresoCod = congresoCod
                            )
                        } else {
                            println("CODIGO CONGRESO: $")
                            scannerViewModel.registrarRefrigerio(
                                documentoParticipante = data.numDocumento,
                                congresoCod = congresoCod
                            )
                        }
                    } catch (e: Exception) {
                        scannerViewModel.mostrarErrorQR()
                        println("QR ERROR decode: ${e.message} | raw: $qr")
                    }
                }
            }
        )

        if (isCameraActive.value) {
            // Marco encima
            MarcoCamera(
                modifier = Modifier.size(marcoSize)
            )
        }
    }
}