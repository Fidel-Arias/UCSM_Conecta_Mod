package org.ucsmconecta.mod.service.camera

import platform.AVFoundation.*
import platform.UIKit.*
import platform.darwin.dispatch_get_main_queue

@OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)
class QRScannerViewController(
    private val onDetected: (String) -> Unit
) : UIViewController(nibName = null, bundle = null),
    AVCaptureMetadataOutputObjectsDelegateProtocol {

    private val captureSession = AVCaptureSession()
    override fun viewDidLoad() {
        super.viewDidLoad()

        val videoCaptureDevice = AVCaptureDevice.defaultDeviceWithMediaType(AVMediaTypeVideo)
        val videoInput = AVCaptureDeviceInput.deviceInputWithDevice(videoCaptureDevice!!, error = null)

        if (videoInput != null && captureSession.canAddInput(videoInput)) {
            captureSession.addInput(videoInput)
        } else {
            return
        }

        val metadataOutput = AVCaptureMetadataOutput()
        if (captureSession.canAddOutput(metadataOutput)) {
            captureSession.addOutput(metadataOutput)
            metadataOutput.setMetadataObjectsDelegate(this, dispatch_get_main_queue())
            metadataOutput.metadataObjectTypes = listOf(AVMetadataObjectTypeQRCode)
        }

        val previewLayer = AVCaptureVideoPreviewLayer(session = captureSession)
        previewLayer.videoGravity = AVLayerVideoGravityResizeAspectFill
        previewLayer.frame = view.layer.bounds
        view.layer.addSublayer(previewLayer)

        captureSession.startRunning()
    }

    override fun captureOutput(
        output: AVCaptureOutput,
        didOutputMetadataObjects: List<*>,
        fromConnection: AVCaptureConnection
    ) {
        val metadataObject = didOutputMetadataObjects.firstOrNull() as? AVMetadataMachineReadableCodeObject
        val stringValue = metadataObject?.stringValue
        if (stringValue != null) {
            captureSession.stopRunning()
            onDetected(stringValue)
        }
    }
}