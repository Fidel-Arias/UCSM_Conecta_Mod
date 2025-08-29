package org.ucsmconecta.mod.components.camera

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import org.ucsmconecta.mod.service.settings.openAppSettings

@Composable
fun CameraWithFrame() {
    val marcoSize = 250.dp
    val isCameraActive = remember { mutableStateOf(false) }
    val isPermanentlyDenied = remember { mutableStateOf(false) }

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