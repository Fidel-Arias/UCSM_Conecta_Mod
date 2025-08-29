package org.ucsmconecta.mod.components.camera

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.ucsmconecta.mod.service.camera.CheckCameraPermissions
import ucsmconectamod.composeapp.generated.resources.Res
import ucsmconectamod.composeapp.generated.resources.camera_no_permission

@Composable
fun CameraNoAccess() {
    val cameraIconNoPermission = painterResource(Res.drawable.camera_no_permission)
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Image(
            painter = cameraIconNoPermission,
            contentDescription = "Disable Camera",
            alignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .size(150.dp)
        )
        Text(
            text = "Permisos de acceso a la cámara denegados",
            fontSize = 14.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        CheckCameraPermissions()
    }
}