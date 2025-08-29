package org.ucsmconecta.mod.components.camera

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitView
import org.ucsmconecta.mod.service.camera.QRScannerViewController
import platform.AVFoundation.AVAuthorizationStatusAuthorized
import platform.AVFoundation.AVAuthorizationStatusDenied
import platform.AVFoundation.AVAuthorizationStatusNotDetermined
import platform.AVFoundation.AVAuthorizationStatusRestricted
import platform.AVFoundation.AVCaptureDevice
import platform.AVFoundation.AVMediaTypeVideo
import platform.AVFoundation.authorizationStatusForMediaType
import platform.AVFoundation.requestAccessForMediaType

@Composable
actual fun CameraPreview(
    reductionFactor: Float,
    onCameraStatusChanged: (Boolean, Boolean) -> Unit
) {
    val granted = remember { mutableStateOf(false) }
    val permanentlyDenied = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val status = AVCaptureDevice.authorizationStatusForMediaType(AVMediaTypeVideo)
        when (status) {
            AVAuthorizationStatusAuthorized -> {
                granted.value = true
            }
            AVAuthorizationStatusNotDetermined -> {
                AVCaptureDevice.requestAccessForMediaType(AVMediaTypeVideo) { accessGranted ->
                    granted.value = accessGranted
                }
            }
            AVAuthorizationStatusDenied, AVAuthorizationStatusRestricted -> {
                granted.value = false
                permanentlyDenied.value = true
            }
        }
        onCameraStatusChanged(granted.value, permanentlyDenied.value)
    }

    if (granted.value) {
        // Aquí pondrías tu vista previa QR
    } else {
        CameraNoAccess()
    }
}
