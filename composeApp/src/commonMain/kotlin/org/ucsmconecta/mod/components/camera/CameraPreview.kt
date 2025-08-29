package org.ucsmconecta.mod.components.camera

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun CameraPreview(
    reductionFactor: Float,
    onCameraStatusChanged: (active: Boolean, permanentlyDenied: Boolean) -> Unit
)