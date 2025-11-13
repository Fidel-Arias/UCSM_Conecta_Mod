package org.ucsmconecta.mod.data.model.qr

import kotlinx.serialization.Serializable

@Serializable
data class QRCode(
    val nombres: String,
    val numDocumento: String
)
