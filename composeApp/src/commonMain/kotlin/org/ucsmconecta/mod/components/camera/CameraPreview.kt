package org.ucsmconecta.mod.components.camera

import androidx.compose.runtime.Composable

@Composable
expect fun CameraPreview(
    reductionFactor: Float,
    onCameraStatusChanged: (active: Boolean, permanentlyDenied: Boolean) -> Unit,
    onQrDetected: (String) -> Unit
)