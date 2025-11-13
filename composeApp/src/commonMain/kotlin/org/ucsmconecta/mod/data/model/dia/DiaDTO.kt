package org.ucsmconecta.mod.data.model.dia

import kotlinx.serialization.Serializable
import org.ucsmconecta.mod.data.model.congreso.DataCongresoResponse

@Serializable
data class DataResponseDia(
    val id: Long,
    val fecha: String,
    val congreso: DataCongresoResponse
)