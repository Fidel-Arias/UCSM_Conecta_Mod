package org.ucsmconecta.mod.service.camera

import androidx.compose.runtime.Composable
import platform.AVFoundation.AVCaptureDevice
import platform.AVFoundation.AVMediaTypeVideo
import platform.AVFoundation.requestAccessForMediaType

@Composable
actual fun CheckCameraPermissions() {
    // Request camera access
    AVCaptureDevice.requestAccessForMediaType(AVMediaTypeVideo) { granted ->
        if (granted) {
            // Camera access granted
            println("Camera access granted")
        } else {
            // Camera access denied
            println("Camera access denied")
        }
    }
}