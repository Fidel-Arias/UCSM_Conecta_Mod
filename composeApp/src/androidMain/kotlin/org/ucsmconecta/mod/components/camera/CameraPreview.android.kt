package org.ucsmconecta.mod.components.camera

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import android.view.ViewGroup
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.OptIn
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import org.ucsmconecta.mod.service.context.findActivity

@OptIn(ExperimentalGetImage::class)
@Composable
actual fun CameraPreview(
    reductionFactor: Float,
    onCameraStatusChanged: (Boolean, Boolean) -> Unit,
    onQrDetected: (String) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var hasPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context, Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }
    var permanentlyDenied by remember { mutableStateOf(false) }

    val windowInfo = LocalWindowInfo.current
    val containerSize = windowInfo.containerSize

    // Tomamos el ancho de la ventana en píxeles y reducimos
    val sizePx = (containerSize.width * reductionFactor).toInt()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            hasPermission = granted
            if (!granted && !ActivityCompat.shouldShowRequestPermissionRationale(
                    context.findActivity()!!,
                    Manifest.permission.CAMERA
                )
            ) {
                permanentlyDenied = true
            }
        }
    )

    // Vuelve a verificar permisos al reanudar la pantalla
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                val granted = ContextCompat.checkSelfPermission(
                    context, Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
                hasPermission = granted
                if (!granted && !ActivityCompat.shouldShowRequestPermissionRationale(
                        context.findActivity()!!,
                        Manifest.permission.CAMERA
                    )
                ) {
                    permanentlyDenied = true
                } else {
                    permanentlyDenied = false
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    LaunchedEffect(Unit) {
        if (!hasPermission) {
            launcher.launch(Manifest.permission.CAMERA)
        }
    }

    // Avisamos al padre si la cámara está activa o no
    LaunchedEffect(hasPermission, permanentlyDenied) {
        onCameraStatusChanged(hasPermission, permanentlyDenied)
    }

    if (hasPermission) {
        AndroidView(
            modifier = Modifier
                .size(sizePx.dp) // cuadrado reducido
                .clip(RoundedCornerShape(16.dp)) // 👈 Esquinas redondeadas
                .background(Color.Black),
            factory = { context ->
                val previewView = PreviewView(context).apply {
                    scaleType = PreviewView.ScaleType.FILL_CENTER // 👈 Recorta exceso
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }

                val barcodeScanner = BarcodeScanning.getClient()

                val analysis = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()
                    .also { analysisUseCase ->
                        analysisUseCase.setAnalyzer(
                            ContextCompat.getMainExecutor(context)
                        ) { imageProxy ->
                            val mediaImage = imageProxy.image
                            if (mediaImage != null) {
                                val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
                                barcodeScanner.process(image)
                                    .addOnSuccessListener { barcodes ->
                                        barcodes.firstOrNull()?.rawValue?.let { qr ->
                                            // Aquí puedes emitir el QR leído a tu lógica
                                            Log.d("QRScanner", "QR detectado: $qr")
                                            onQrDetected(qr)
                                        }
                                    }
                                    .addOnCompleteListener {
                                        imageProxy.close()
                                    }
                            } else {
                                imageProxy.close()
                            }
                        }
                    }

                // Configurar cámara
                val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
                val preview = Preview.Builder().build().apply {
                    setSurfaceProvider(previewView.surfaceProvider)
                }
                val cameraProvider = cameraProviderFuture.get()
                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        preview,
                        analysis
                    )
                } catch (exc: Exception) {
                    exc.printStackTrace()
                }

                previewView
            }
        )
    } else {
        CameraNoAccess()
    }
}